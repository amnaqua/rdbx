package com.boots.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Column(unique = true)
    private String phoneNumber;
    private Double purchaseSum;
    private Integer visitsCount;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(Long id,String username, String password, String phoneNumber, Double purchaseSum, Integer visitsCount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.purchaseSum = purchaseSum;
        this.visitsCount = visitsCount;
    }

    public User() {
        this.visitsCount = 0;
        this.purchaseSum = 0.0;
    }

    public User(String username, String password, String phoneNumber, Double purchaseSum, Integer visitsCount) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.purchaseSum = purchaseSum;
        this.visitsCount = visitsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(Double purchaseSum) {
        this.purchaseSum = purchaseSum;
    }

    public Integer getVisitsCount() {
        return visitsCount;
    }

    public void  setVisitsCount(Integer visitsCount) {
        this.visitsCount = visitsCount;
    }
}
