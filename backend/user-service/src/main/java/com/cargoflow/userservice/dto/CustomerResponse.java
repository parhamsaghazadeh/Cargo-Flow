package com.cargoflow.userservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerResponse {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;
    private LocalDateTime createdAt;

    public CustomerResponse() {}

    public CustomerResponse(Long id, String name, String lastName, Integer age, String email, String phone, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public CustomerResponse(Long id, String name, String lastName, Integer age, String email, String phone,
                            LocalDate dateOfBirth, String address, LocalDateTime createdAt) {
        this(id, name, lastName, age, email, phone, createdAt);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
