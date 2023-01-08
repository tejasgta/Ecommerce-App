package com.avenger.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avenger.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
