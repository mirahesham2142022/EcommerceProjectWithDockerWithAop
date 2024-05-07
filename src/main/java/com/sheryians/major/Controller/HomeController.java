package com.sheryians.major.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
    	model.addAttribute("cartCount",GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
      
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "shop";
    }
   // http://localhost:4335/shop/category13
    @GetMapping("/shop/category{id}")
    public String shByCategory(Model model,@PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("products", productService.GetAllProductsByCategoryId(id));
        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String ViewProduct(Model model,@PathVariable Long id) {
        model.addAttribute("product",productService.GetProductId(id).get());
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "viewProduct";
    }
    
}
