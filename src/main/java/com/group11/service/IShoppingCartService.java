package com.group11.service;



import com.group11.entity.ProductEntity;
import com.group11.entity.ShoppingCartEntity;
import com.group11.entity.UserEntity;

import java.util.List;

public interface IShoppingCartService {

    ShoppingCartEntity addProductToCart(UserEntity user, ProductEntity product);
    List<ProductEntity> findProductsByUserId(Long userID);
    boolean removeProductFromCart(Long userId, Long productId);
}
