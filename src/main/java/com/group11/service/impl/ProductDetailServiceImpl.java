package com.group11.service.impl;
import com.group11.entity.ProductDetailEntity;
import com.group11.repository.ProductDetailRepository;
import com.group11.service.IProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductDetailServiceImpl implements IProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;



	@Override
	public List<ProductDetailEntity> findAll() {
		// TODO Auto-generated method stub
		return productDetailRepository.findAll();
	}


	@Override
	public Optional<ProductDetailEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return productDetailRepository.findById(id);
	}


	@Override
	public ProductDetailEntity save(ProductDetailEntity productDetailEntity) {
		// TODO Auto-generated method stub
		return productDetailRepository.save(productDetailEntity);
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		productDetailRepository.deleteById(id);
	}


}
