package com.group11.restcontroller;

import com.group11.entity.ProductEntity;
import com.group11.entity.ProductStatus;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @RequestMapping("/newest")
    public ResponseEntity<List<ProductEntity>> getNewestProducts() {
        List<ProductEntity> newestProducts = productService.getNewestProducts();
        return ResponseEntity.ok(newestProducts);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

}
