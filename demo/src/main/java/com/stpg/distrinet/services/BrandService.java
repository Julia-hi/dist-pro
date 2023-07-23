package com.stpg.distrinet.services;

import com.stpg.distrinet.models.Brand;
import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.repository.BrandRepository;
import com.stpg.distrinet.repository.DocumentRepository;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;


    public BrandRepository crud() {
        return brandRepository;
    }
    public Brand getById(Long id) {
        Optional<Brand> d = brandRepository.findById(id);
        if (d.isEmpty()){
            throw new ServiceException(String.format("Brand with id = % does not exist"));
        }
        else {
            return d.get();
        }
    }

    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }
}
