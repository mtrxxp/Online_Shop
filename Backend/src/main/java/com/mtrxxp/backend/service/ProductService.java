package com.mtrxxp.backend.service;

import com.mtrxxp.backend.model.ProductModel;
import com.mtrxxp.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }
    public ProductModel getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductModel addProduct(ProductModel product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());

        return productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public void updateProduct(ProductModel product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        productRepository.save(product);
    }

    public List<ProductModel> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }
}
