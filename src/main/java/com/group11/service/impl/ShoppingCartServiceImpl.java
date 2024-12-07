package com.group11.service.impl;


import com.group11.entity.ProductEntity;
import com.group11.entity.ShoppingCartEntity;
import com.group11.entity.UserEntity;
import com.group11.repository.ProductRepository;
import com.group11.repository.ShoppingCartRepository;
import com.group11.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ShoppingCartEntity addProductToCart(UserEntity user, ProductEntity product) {
        // Find the shopping cart by user
        Optional<ShoppingCartEntity> optionalCart = shoppingCartRepository.findShoppingCartEntityByUser(user);

        ShoppingCartEntity shoppingCart;
        if (optionalCart.isPresent()) {
            // Cart already exists, add the product
            shoppingCart = optionalCart.get();
        } else {
            // Create new cart for user
            shoppingCart = new ShoppingCartEntity();
            shoppingCart.setUser(user);
        }
        List<ProductEntity> products = shoppingCart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        // Check if the product already exists in the cart
        boolean isProductInCart = products.stream()
                .anyMatch(existingProduct -> existingProduct.getProductID().equals(product.getProductID()));

        if (!isProductInCart) {
            // Add the product only if it does not already exist
            products.add(product);
            shoppingCart.setProducts(products);
        }

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ProductEntity> findProductsByUserId(Long userID) {
        return shoppingCartRepository.findProductsByUserId(userID);
    }

    @Override
    @Transactional
    public boolean removeProductFromCart(Long userId, Long productId) {
        // Lấy giỏ hàng của khách hàng
        ShoppingCartEntity cart = shoppingCartRepository.findShoppingCartEntityByUser_UserID(userId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found for customer ID: " + userId));

        // Tìm sản phẩm cần xóa
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Xóa sản phẩm khỏi danh sách
        cart.getProducts().remove(product);

        // Lưu lại giỏ hàng
        shoppingCartRepository.save(cart);
        return true;
    }

}
