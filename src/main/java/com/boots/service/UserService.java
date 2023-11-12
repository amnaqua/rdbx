package com.boots.service;

import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void addVisit(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalAccessError::new);
        int visits = user.getVisitsCount();
        user.setVisitsCount(visits + 1);
        userRepository.save(user);
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

        return true;
    }

//    public void saveUpdatedUser(User user) {
//        User userFromDB = userRepository.findByUsername(user.getUsername());
//
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//
//        userRepository.save(user);
//    }

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

        Optional.ofNullable(username).filter(n -> !n.isEmpty()).ifPresent(user::setUsername);
        Optional.ofNullable(password).filter(p -> !p.isEmpty()).ifPresent(user::setPassword);
        Optional.ofNullable(phoneNumber).filter(pn -> !pn.isEmpty()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(purchaseSum).ifPresent(user::setPurchaseSum);
        Optional.ofNullable(visitCount).ifPresent(user::setVisitsCount);

        Optional.ofNullable(roles).filter(r -> !r.isEmpty()).ifPresent(rStr -> {
            Set<Role> roleSet = Stream.of(rStr.split(";"))
                    .map(role -> roleRepository.findFirstByName(role)
                            .orElseThrow(() -> new IllegalStateException("Invalid role: " + role)))
                    .collect(Collectors.toSet());
            user.setRoles(roleSet);
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

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
