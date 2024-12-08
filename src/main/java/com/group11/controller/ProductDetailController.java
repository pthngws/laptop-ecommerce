package com.group11.controller;

import com.group11.entity.ProductEntity;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductDetailController {

    @RequestMapping("/{id}")
   public String productDetail(@PathVariable int id) {
        return "productDetails";
    }
}
