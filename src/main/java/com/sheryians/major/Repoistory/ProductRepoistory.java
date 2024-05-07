package com.sheryians.major.Repoistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
@Repository
public interface ProductRepoistory extends JpaRepository<Product,Long>{

	//CategoryId
	List<Product> findAllByCategoryId(int id);
}
