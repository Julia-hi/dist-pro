package com.stpg.distrinet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stpg.distrinet.models.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
