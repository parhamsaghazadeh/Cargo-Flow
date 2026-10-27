package com.cargoflow.userservice.dto;

import java.util.List;

public class MeResponse {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String username;
    private String keycloakId;
    private List<String> roles;

    public MeResponse() {}

    public MeResponse(Long id, String name, String lastName, Integer age, String email, String phone,
                      String username, String keycloakId, List<String> roles) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.keycloakId = keycloakId;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getKeycloakId() { return keycloakId; }
    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}