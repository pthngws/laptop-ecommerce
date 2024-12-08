package com.group11.service;


import com.group11.entity.ProductDetailEntity;

import java.util.List;
import java.util.Optional;

public interface IProductDetailService {

	List<ProductDetailEntity> findAll();
	
	Optional<ProductDetailEntity> findById(Long id);
	
	ProductDetailEntity save(ProductDetailEntity productDetailEntity);

	void deleteById(Long id);

}
