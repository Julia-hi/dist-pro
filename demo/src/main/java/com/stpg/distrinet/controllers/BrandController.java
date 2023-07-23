package com.stpg.distrinet.controllers;


import com.stpg.distrinet.models.Brand;
import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.services.BrandService;
import com.stpg.distrinet.services.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("")
    public List<Brand> getDocuments() {
        List<Brand> brandList = brandService.getBrands();
        return brandList;

    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable("id") Long id){

        return brandService.getById(id);
    }


}
