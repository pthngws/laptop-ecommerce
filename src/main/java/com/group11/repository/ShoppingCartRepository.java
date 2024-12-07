package com.group11.repository;


import com.group11.entity.ProductEntity;
import com.group11.entity.ShoppingCartEntity;
import com.group11.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity,Long> {

    Optional<ShoppingCartEntity> findShoppingCartEntityByUser(UserEntity user);
    Optional<ShoppingCartEntity> findShoppingCartEntityByUser_UserID(Long id);

    @Query("SELECT sc.products FROM ShoppingCartEntity sc WHERE sc.user.userID = :userID")
    List<ProductEntity> findProductsByUserId(@Param("userID") Long userID);

    @Modifying
    @Query("DELETE FROM ShoppingCartEntity sc WHERE sc.user.userID = :customerId AND :product MEMBER OF sc.products")
    void removeProductFromCart(@Param("userId") Long userId, @Param("product") ProductEntity product);
}
