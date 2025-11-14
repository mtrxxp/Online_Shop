package com.mtrxxp.backend.controller;

import com.mtrxxp.backend.model.ProductModel;
import com.mtrxxp.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductModel> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable int id) {
        ProductModel product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        ProductModel product = productService.getProductById(id);
        return ResponseEntity.ok(product.getImageData());
    }

    @PostMapping("/product")
    public ResponseEntity<ProductModel> addProduct(@RequestPart ProductModel product, @RequestPart MultipartFile imageFile) {
        try {
            ProductModel savedProduct = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestPart ProductModel product, @RequestPart MultipartFile imageFile) {
        try{
            productService.updateProduct(product, imageFile);
            return ResponseEntity.ok("Updated");
        }catch(IOException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductModel>> searchProduct(@RequestParam String keyword) {
        List<ProductModel> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }
}
