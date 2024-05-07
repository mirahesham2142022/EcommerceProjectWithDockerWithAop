package com.sheryians.major.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.major.Repoistory.CategoryRepoistory;
import com.sheryians.major.model.Category;

import java.util.*;
@Service
public class CategoryService {
	@Autowired
	CategoryRepoistory categoryRepo;
	public List<Category > getAllCategories()
	{
		return categoryRepo.findAll();
	}
	public void AddCategory (Category category)
	{
		categoryRepo.save(category);
	}
	public void DeleteCategory (int id)
	{
		categoryRepo.deleteById(id);
	}
	public Optional <Category> getCategoryById(int id)
	{
		return categoryRepo.findById(id);
	}
}
