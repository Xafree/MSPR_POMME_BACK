package com.gostyle.webservice.service;

import com.gostyle.webservice.dao.ProductRepository;
import com.gostyle.webservice.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product){
        return repository.save(product);
    }

    public List<Product> addProducts(List<Product> productsList){
        return repository.saveAll(productsList);
    }

    public Product getProductById(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public String deleteProduct(int id){
        repository.deleteById(id);
        if ( repository.existsById(id) ) {
            return "Product with " + id + " has been successfully removed";
        }
        else {
            return "The product with " + id + " has not been removed";
        }

    }

    public Product updateProduct(Product product){
        Product productToUpdate = repository.findById(product.getId()).orElse(null);

        if ( productToUpdate != null ) {
            product.setType(product.getType());
            product.setDescription(product.getDescription());
            product.setBarcode(product.getBarcode());
            product.setPrix(product.getPrix());
            repository.save(productToUpdate);
        }

        return productToUpdate;
    }


}
