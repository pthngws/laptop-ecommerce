package com.group11.restcontroller;

import com.group11.dto.ProductDTO;
import com.group11.dto.request.CartItemRequest;
import com.group11.dto.response.CartProductDTO;
import com.group11.entity.ImageItemEntity;
import com.group11.entity.ProductEntity;
import com.group11.entity.UserEntity;
import com.group11.service.IJwtService;
import com.group11.service.IProductService;
import com.group11.service.IShoppingCartService;
import com.group11.service.IUserService;
import com.group11.service.impl.JwtServiceImpl;
import com.group11.service.impl.ProductServiceImpl;
import com.group11.service.impl.ShoppingCartServiceImpl;
import com.group11.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    @Autowired
    IJwtService jwtService = new JwtServiceImpl();

    @Autowired
    IProductService productService = new ProductServiceImpl();

    @Autowired
    IShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

    @Autowired
    IUserService userService = new UserServiceImpl();

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CartItemRequest cartItemRequest) {
        try {
            // Lấy token từ header
            String token = authorizationHeader.replace("Bearer ", "");

            // Trích xuất userid từ token
            Long userId = jwtService.extractUserId(token);

            UserEntity user = userService.findById(userId);

            // Tìm sản phẩm theo productId
            ProductEntity product = productService.findById(cartItemRequest.getProductId());

            shoppingCartService.addProductToCart(user, product); // Thêm sản phẩm vào giỏ hàng
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping("products")
    public ResponseEntity<List<CartProductDTO>> getProductsByToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Lấy token từ Authorization header
            String token = authorizationHeader.substring(7); // Bỏ chữ "Bearer "

            // Trích xuất userId từ token
            Long userId = jwtService.extractUserId(token);

            // Lấy danh sách sản phẩm trong giỏ hàng của user
            List<ProductEntity> products = shoppingCartService.findProductsByUserId(userId);

            // Chuyển đổi từ ProductEntity sang CartProductDTO
            List<CartProductDTO> productDTOs = products.stream()
                    .map(product -> new CartProductDTO(
                            product.getProductID(),
                            product.getName(),
                            product.getDetail().getImages().isEmpty() ? "" : product.getDetail().getImages().get(0).getImageUrl(),
                            product.getPrice()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long productId) {
        try {
            String token = authorizationHeader.substring(7); // Bỏ chữ "Bearer "

            // Trích xuất userId từ token
            Long userId = jwtService.extractUserId(token);

            boolean success = shoppingCartService.removeProductFromCart(userId, productId);
            if (success) {
                return ResponseEntity.ok("Product removed successfully");
            } else {
                return ResponseEntity.status(404).body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while removing the product");
        }
    }

}
