package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller("ManagementProductController")
@RequestMapping("/management/product")
public class ProductController {
    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("be/productList");
        return modelAndView;
    }

}
