package com.sheryians.major.Repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sheryians.major.model.Category;
@Repository
public interface CategoryRepoistory extends JpaRepository<Category,Integer> {

}
