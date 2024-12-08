package com.group11.restcontroller;

import com.group11.entity.ProductEntity;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductDetailRestController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @RequestMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductDetails(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.findProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }
}
