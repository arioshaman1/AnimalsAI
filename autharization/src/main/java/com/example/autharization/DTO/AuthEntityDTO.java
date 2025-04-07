package com.example.autharization.DTO;


import com.example.autharization.entities.AuthEntity;

public class AuthEntityDTO {
    private String username;
    private String password;
    private String email;


    public AuthEntityDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AuthEntityDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    public AuthEntity convertToEntity(AuthEntityDTO authDTO) {
        AuthEntity user = new AuthEntity();
        user.setUsername(authDTO.getUsername());
        user.setPassword(authDTO.getPassword());
        user.setEmail(authDTO.getEmail());
        return user;
    }
}