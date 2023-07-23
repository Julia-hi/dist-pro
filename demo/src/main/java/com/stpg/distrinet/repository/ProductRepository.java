package com.stpg.distrinet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stpg.distrinet.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
