package com.group11.service.impl;


import com.group11.entity.ProductEntity;
import com.group11.repository.ProductRepository;
import com.group11.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;


//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private RateRepository rateRepository;

    @Override
    public Page<ProductEntity> searchProducts(@Param("keyword") String keyword, Pageable pageable)
    {
        return productRepository.searchProducts(keyword, pageable);
    }


    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductEntity> searchProducts(String searchName, String manufacturer, String cpu, String gpu,
                                              String operationSystem, Integer minPrice, Integer maxPrice, String disk, String category, Pageable pageable) {
        // Gọi đến repository để lấy danh sách sản phẩm dựa trên các tiêu chí tìm kiếm
        return productRepository.findProductsByCriteria(searchName, manufacturer, cpu, gpu, operationSystem, minPrice, maxPrice, disk, category, pageable);
    }

//    @Override
//    public List<CategoryModel> getAllCategories() {
//        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
//        return categoryEntities.stream()
//                .map(categoryEntity -> {
//                    CategoryModel categoryModel = new CategoryModel();
//                    categoryModel.setCategoryID(categoryEntity.getCategoryID());
//                    categoryModel.setName(categoryEntity.getName());
//                    categoryModel.setDescription(categoryEntity.getDescription());
//                    return categoryModel;
//                })
//                .collect(Collectors.toList());
//    }


    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductEntity> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public double calculateAverageRating(ProductEntity product) {
        return 0;
    }

    @Override
    public int getReviewCount(ProductEntity product) {
        return 0;
    }

//    @Override
//    public double calculateAverageRating(ProductEntity product) {
//        Double avgRating = rateRepository.findAverageRatingByProductId(product.getProductID());
//        return avgRating != null ? avgRating : 0.0;
//    }
//
//    @Override
//    public int getReviewCount(ProductEntity product) {
//        Integer reviewCount = rateRepository.findReviewCountByProductId(product.getProductID());
//        return reviewCount != null ? reviewCount : 0;
//    }


	@Override
	public List<ProductEntity> findByName(String producName) {
		// TODO Auto-generated method stub
		return productRepository.findByName(producName);
	}

	@Override
	public Page<ProductEntity> findAllDistinctByName(Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findAllDistinctByName(pageable);
	}

	
	@Override
    public Page<ProductEntity> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductEntity> getNewestProducts() {
        return productRepository.findTop10NewestProducts();
    }


}
