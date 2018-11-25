package pocketestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pocketestore.model.Product;
import pocketestore.service.IProductService;
import pocketestore.serviceimpl.ProductService;

@Controller("FEProductController")
@RequestMapping("/product/")
public class ProductController {
    @RequestMapping("/{productId}")
    public ModelAndView detail(@PathVariable String productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        IProductService productService = new ProductService();
        Product product = productService.getProductById(productId);
        modelAndView.addObject("productInfo", product);
        modelAndView.setViewName("fe/product");
        return modelAndView;
    }
}
