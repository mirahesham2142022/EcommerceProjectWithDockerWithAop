package com.sheryians.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.major.Repoistory.CategoryRepoistory;
import com.sheryians.major.Repoistory.ProductRepoistory;
import com.sheryians.major.model.*;
@Service
public class ProductService {
	@Autowired
	 ProductRepoistory ProductRepo;

	public List <Product> getAllProducts ()
	{
		return ProductRepo.findAll();
	}
	public void AddProduct(Product product)
	{
		ProductRepo.save(product);
	}
	public void removePrductById(Long id)
	{
		ProductRepo.deleteById(id);
	}
	public Optional <Product> GetProductId(Long id)
	{
		return ProductRepo.findById(id);
		
		
	}
	public List<Product>GetAllProductsByCategoryId(int CategoryId)
	{
		return ProductRepo.findAllByCategoryId(CategoryId);
		
	}
	 public double applyDiscountCode(String discountCode, List<Product> cart) {
	 
	        if ("DISCOUNT20".equals(discountCode)) {

	            double totalPrice = cart.stream().mapToDouble(Product::getPrice).sum();
	            // Apply 20% discount
	            double discountAmount = totalPrice * 0.2; // 20%
	            // Return the discount amount
	            return discountAmount;
	        } else {
	            // If discount code is not valid, return 0
	            return 0;
	        }
	    }
}
