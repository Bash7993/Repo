package com.wipro.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
