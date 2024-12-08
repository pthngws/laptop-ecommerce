package com.group11.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.dto.ProductDTO;
import com.group11.dto.request.ProductRequest;
import com.group11.entity.*;
import com.group11.repository.*;
import com.group11.service.IJwtService;
import com.group11.service.IProductService;
import com.group11.service.impl.JwtServiceImpl;
import com.group11.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    IJwtService jwtService = new JwtServiceImpl();
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
    public ResponseEntity<Page<ProductEntity>> getAllProducts(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ProductEntity addProduct(@RequestBody ProductEntity productEntity) {
        // Lấy thông tin Category và Manufacturer
        CategoryEntity category = categoryRepository.findById(productEntity.getCategory().getCategoryID())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ManufacturerEntity manufacturer = manufacturerRepository.findById(productEntity.getManufacturer().getId())
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

        // Lưu ProductDetail nếu có thông tin chi tiết
        ProductDetailEntity productDetail = productEntity.getDetail();
        if (productDetail != null) {
            // Lưu thông tin chi tiết sản phẩm
            productDetailRepository.save(productDetail);

            // Lưu danh sách hình ảnh nếu có
            List<ImageItemEntity> images = productDetail.getImages();
            if (images != null && !images.isEmpty()) {
                for (ImageItemEntity image : images) {
                    image.setProductDetail(productDetail); // Gắn chi tiết sản phẩm cho mỗi hình ảnh
                    imageItemRepository.save(image);
                }
            }
        }

        // Cập nhật thông tin category, manufacturer cho sản phẩm
        productEntity.setCategory(category);
        productEntity.setManufacturer(manufacturer);

        // Lưu sản phẩm vào cơ sở dữ liệu
        return productRepository.save(productEntity);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProduct(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long productId) {
        try {
            String token = authorizationHeader.substring(7); // Bỏ chữ "Bearer "

            // Trích xuất userId từ token
            Long userId = jwtService.extractUserId(token);
            // Tìm sản phẩm theo id
            ProductEntity product = productService.findById(productId);
            if(product.getStatus() == ProductStatus.AVAILABLE)
            {
                product.setStatus(ProductStatus.UNAVAILABLE);
                productRepository.save(product);
                return ResponseEntity.ok("Product removed successfully");
            }
            else {
                return ResponseEntity.status(404).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while removing the product");
        }
    }


}
