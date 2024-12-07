package com.group11.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.dto.ProductDTO;
import com.group11.dto.request.ProductRequest;
import com.group11.entity.*;
import com.group11.repository.*;
import com.group11.service.IProductService;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    IProductService productService = new ProductServiceImpl();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ImageItemRepository imageItemRepository;
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

//    @PostMapping("/add")
//    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
//        try {
//            // Map dữ liệu từ ProductRequest sang ProductEntity
//            ProductDetailEntity productDetail = new ProductDetailEntity();
//            productDetail.setRAM(productRequest.getDetail().getRAM());
//            productDetail.setCPU(productRequest.getDetail().getCPU());
//            productDetail.setGPU(productRequest.getDetail().getGPU());
//
//            // Thêm hình ảnh từ URL
//            List<ImageItemEntity> imageItems = productRequest.getImageUrls().stream().map(url -> {
//                ImageItemEntity imageItem = new ImageItemEntity();
//                imageItem.setImageUrl(url);
//                imageItem.setProductDetail(productDetail);
//                return imageItem;
//            }).toList();
//            productDetail.setImages(imageItems);
//
//            ProductEntity product = new ProductEntity();
//            product.setName(productRequest.getName());
//            product.setPrice(productRequest.getPrice());
////            product.setCategory(categoryRepository.fid(productRequest.getCategory())); // Tìm category
////            product.setManufacturer(productService.getManufacturerByName(productRequest.getManufacturer())); // Tìm manufacturer
//            product.setDetail(productDetail);
//
//            // Lưu sản phẩm vào DB
//            productService.saveProduct(product);
//
//            return ResponseEntity.ok("Thêm sản phẩm thành công!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thêm sản phẩm thất bại: " + e.getMessage());
//        }
//    }


}
