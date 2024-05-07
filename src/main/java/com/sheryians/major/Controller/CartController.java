package com.sheryians.major.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.ProductService;

@Controller
public class CartController {

    @Autowired
    ProductService productservice;

    @GetMapping("/addToCart/{id}")
    public String addTocart(@PathVariable Long id) {
        GlobalData.cart.add(productservice.GetProductId(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String Cart(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String CartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam(value = "discountCode", required = false) String discountCode, Model model) {
        double totalPrice = calculateTotalPrice();
        double discountedPrice = applyDiscount(discountCode, totalPrice);

        model.addAttribute("total", discountedPrice);
        model.addAttribute("discountApplied", discountedPrice < totalPrice);
        return "checkout";
    }

    private double calculateTotalPrice() {
        return GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
    }

    private double applyDiscount(String discountCode, double totalPrice) {
        // Example: Apply a fixed discount of 10% for code "DISCOUNT10"
        if ("DISCOUNT10".equals(discountCode)) {
            return totalPrice * 0.9; // 10% discount
        } else {
            return totalPrice; // No discount applied
        }
    }
}
