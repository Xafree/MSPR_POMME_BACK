package com.gostyle.webservice.controller;

import com.gostyle.webservice.entities.Product;
import com.gostyle.webservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product){
       return service.addProduct(product);
    }

    @PostMapping("/products")
    public List<Product> addProducts(@RequestBody List<Product> productsList){
        return service.addProducts(productsList);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id){
        return service.getProductById(id);
    }

    @CrossOrigin
    @GetMapping("/products")
    public List<Product> getProducts(){
        return  service.getProducts();
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }


}
