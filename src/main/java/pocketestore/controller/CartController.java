package pocketestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pocketestore.model.Cart;

@Controller("FECartController")
public class CartController extends BaseController {
    @RequestMapping("/cart/index")
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView();
        Cart cart = this.getCartFromSession();
        if (cart == null) {
            cart = new Cart();
        }
        modelAndView.addObject("cartData", cart);
        modelAndView.setViewName("fe/cart");
        return modelAndView;
    }
}
