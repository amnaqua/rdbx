package com.boots.service;

import com.boots.entity.Purchase;
import com.boots.entity.Smsc;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.PurchaseRepository;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.zxing.*;
import com.google.zxing.client.j2se.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> managerUsers() {
        return userRepository.findByRoleName("ROLE_USER");
    }

    public void addVisit(Long id, Double amount) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        int visits = user.getVisitsCount();
        user.setVisitsCount(visits + 1);
        user.setPurchaseSum(user.getPurchaseSum() + amount - discount(user.getVisitsCount()));
        userRepository.save(user);
    }

    public Double discount(Integer visits) {
        if (visits == 3 || visits == 8) {
            return 5.0;
        } else if (visits == 5 || visits == 10) {
            return 10.0;
        }
        return 0.0;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findFirstByName("ROLE_USER").orElseThrow(IllegalStateException::new)));

        userRepository.save(user);

        try {
            String qrCodeData = generateQRCode(user.getUsername());
            System.out.println(qrCodeData);

            Smsc smsc = new Smsc("rdbx", "ea1c2o1m");
            String[] result = smsc.send_sms(user.getPhoneNumber(), qrCodeData, 0,
                    "", "", 0, "", "");
            System.out.println("ID сообщения: " + Arrays.toString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean numberValidator(String phoneNumber) {
        String cleanedPhoneNumber = phoneNumber.replaceAll("[\\s\\-()]", "");
        String pattern = "^(\\+7|8)\\d{10}$";
        return cleanedPhoneNumber.matches(pattern);
    }

    public boolean validateEmail(String email) {
        String pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(pattern);
    }

    public String generateQRCode(String data) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pngData);
    }

    public void addUser(User user, String roles) {

        String[] rolesArray = roles.split(";");
        Set<Role> roleSet = new HashSet<>();
        for (String role : rolesArray) {
            Role getRole = roleRepository.findFirstByName(role).orElseThrow(IllegalStateException::new);
            roleSet.add(getRole);
        }
        user.setRoles(roleSet);
        saveUser(user);
    }

    public void updateUser(Long id, String username, String password, String phoneNumber,
                           Double purchaseSum, Integer visitCount, String roles) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));


        Optional.ofNullable(username).filter(u -> !u.isEmpty()).ifPresent(user::setUsername);
        Optional.ofNullable(password).filter(p -> !p.isEmpty()).ifPresent(user::setPassword);
        Optional.ofNullable(phoneNumber).filter(pn -> !pn.isEmpty()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(purchaseSum).ifPresent(user::setPurchaseSum);
        Optional.ofNullable(visitCount).ifPresent(user::setVisitsCount);

        Optional.ofNullable(roles).filter(r -> !r.isEmpty()).ifPresent(rStr -> {
                    user.getRoles().clear();
                    user.getRoles().addAll(roleRepository.findAll());
                    user.getRoles().addAll(Stream.of(rStr.split(";"))
                            .map(role -> roleRepository.findFirstByName(role)
                                    .orElseThrow(() -> new IllegalStateException("Invalid role: " + role)))
                            .collect(Collectors.toSet()));
                });
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        }
    }

    public boolean checkIfUserExistsByPhone(User user) {
        User userFromDB = userRepository.findByPhoneNumber(user.getPhoneNumber());
        return userFromDB != null;
    }

    public List<Purchase> purchasegtList() {
        return purchaseRepository.findAll();
    }
}
