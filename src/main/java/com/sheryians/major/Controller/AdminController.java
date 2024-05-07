package com.sheryians.major.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.major.dto.ProductDto;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller


public class AdminController {

	@Autowired
	public static String uploadDir =  ".\\src\\main\\resources\\static\\productImages";
	@Autowired
	CategoryService categoryservice;
	@Autowired
	ProductService Productservice;
	@GetMapping("/admin")
	public String adminHome()
	{
		return "adminHome";
	}
	@GetMapping("/admin/categories")
	public String GetCategories(Model model)
	{
		model.addAttribute("categories",categoryservice.getAllCategories());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String GetCategoriesAdd(Model model )
	{
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String PostCategoriesAdd(@ModelAttribute("category") Category category )
	{
		categoryservice.AddCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id, Model model) {
	    try {
	        categoryservice.DeleteCategory(id);
	        return "redirect:/admin/categories";
	    } catch (EmptyResultDataAccessException ex) {
	        // Handle the scenario where the entity does not exist
	        model.addAttribute("error", "Category with ID " + id + " does not exist.");
	        return "errorPage"; // Provide a suitable error page
	    } catch (Exception ex) {
	        // Handle other exceptions gracefully
	        model.addAttribute("error", "An unexpected error occurred.");
	        return "errorPage"; // Provide a suitable error page
	    }
	}
	@GetMapping("/admin/categories/update/{id}")
	public String  UpateCategory(@PathVariable int id, Model model) {
		 
		Optional <Category> category =categoryservice.getCategoryById(id);
		if(category.isPresent())
		{
			 model.addAttribute("category",category.get());
			 return "CategoriesAdd";
			 
		}
		else 
		return "404";
	}
	//ProductSection
	@GetMapping("/admin/products")
	public String products(Model model)
	{
		model.addAttribute("products",Productservice.getAllProducts());
		return "products";
		
	}

	  // Mapping to display the form for adding a product
    @GetMapping("/admin/products/add")
    public String showAddProductForm(Model model) {
        // Create a new ProductDto instance
        ProductDto productDTO = new ProductDto();
        // Fetch all categories from the database
        List<Category> categories = categoryservice.getAllCategories();
        // Add categories and productDTO to the model
        model.addAttribute("categories", categories);
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }

    // Mapping to handle the submission of the product form
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO") ProductDto productDto, @RequestParam("productImage") MultipartFile file) throws IOException {
        // Create a new Product instance
        Product product = new Product();
        product.setName(productDto.getName());
        // Fetch the selected category by its ID
        Category category = categoryservice.getCategoryById(productDto.getCategoryId()).orElse(null);
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());

        String imageUUID;
        if (!file.isEmpty()) {
            // Generate a unique filename for the image
            imageUUID = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // Define the path where the image will be stored
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            // Save the image to the specified path
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            // If no image is provided, use the default image name from the DTO
            imageUUID = productDto.getImageName();
        }

        // Set the image name for the product
        product.setImageName(imageUUID);
        // Save the product to the database
        Productservice.AddProduct(product);
        return "redirect:/admin/products";
    }
    // http://localhost:4335/admin/products/delete/6
    @GetMapping("/admin/products/delete/{id}") // corrected mapping
    public String deleteProduct(@PathVariable Long id) {
        Productservice.removePrductById(id);
        return "redirect:/admin/products";
    }

   // http://localhost:4335/admin/products/update/11
    @GetMapping("/admin/products/update/{id}") // corrected mapping
    public String UpdateProduct(@PathVariable Long id, Model model) {
    	 Product product = Productservice.GetProductId(id).get();
        ProductDto productDto=new   ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getName());
        productDto.setImageName(product.getImageName());
        productDto.setWeight(product.getWeight());
        productDto.setCategoryId(product.getCategory().getId());
        model.addAttribute("categories",categoryservice.getAllCategories());
        model.addAttribute("productDTO", productDto); // Add productDTO to the model
        return "productsAdd";
    } 
        
   
        
     
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}