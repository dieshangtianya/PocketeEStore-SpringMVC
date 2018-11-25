package pocketestore.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("OrderController")
@RequestMapping("/management/order")
public class OrderController {
    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("be/orderList");
        return modelAndView;
    }
}
