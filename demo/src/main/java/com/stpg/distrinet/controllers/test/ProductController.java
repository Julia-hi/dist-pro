package com.stpg.distrinet.controllers.test;


import com.stpg.distrinet.models.Product;
import com.stpg.distrinet.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("")
    public List<Product> getProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.size() == 0) {
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setName("Product" + (i + 1));
                product = productRepository.save(product);
                productList.add(product);
            }
        }
        return productList;
    }


}
