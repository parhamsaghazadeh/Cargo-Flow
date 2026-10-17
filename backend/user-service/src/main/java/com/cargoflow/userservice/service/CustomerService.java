package com.cargoflow.userservice.service;

import com.cargoflow.userservice.dto.CustomerRequest;
import com.cargoflow.userservice.dto.CustomerResponse;
import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    CustomerResponse getById(Long id);
    List<CustomerResponse> getAll();
    void delete(Long id);
}
