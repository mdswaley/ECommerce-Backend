package com.example.ecommerce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomePageController {
    @GetMapping("/")
    public String redirect(){
        return "redirect:/swagger-ui.html";
    }
}
