package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pocketestore.config.StaticData;
import pocketestore.controller.BaseController;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.Cart;
import pocketestore.model.CartItem;
import pocketestore.model.Product;
import pocketestore.service.ICartService;
import pocketestore.service.IProductService;
import pocketestore.serviceimpl.CartService;
import pocketestore.serviceimpl.ProductService;

import java.util.List;
import java.util.Map;

@Controller("APICartController")
@RequestMapping("/api/cart")
public class CartController extends BaseController {

    @RequestMapping(value = "/sync/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncCartData(@RequestBody(required = false) Map<String, Object> map) throws Exception {
        List<CartItem> cartItems = null;
        if (map != null) {
            cartItems = (List<CartItem>) map.get("cartItems");
        }
        ICartService cartService = new CartService(this.userSession);
        Cart cart = cartService.syncCartData(cartItems);
        return JsonResponseBuilder.buildSuccessResponse(cart);
    }

    @RequestMapping(value = "/addItem/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addCartItem(String productId, Integer quantity) throws Exception {
        ICartService cartService = new CartService(this.userSession);
        boolean flag = cartService.addToCart(productId, quantity);
        if (flag) {
            Cart cart = this.getCartFromSession();
            return JsonResponseBuilder.buildSuccessResponse(cart);
        }
        return JsonResponseBuilder.buildErrorResponse("服务器出现错误");
    }

    @RequestMapping(value = "/removeItem/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeCartItem(String productId) throws Exception {
        ICartService cartService = new CartService(this.userSession);
        boolean flag = cartService.removeFromCart(productId);
        if (flag) {
            Cart cart = this.getCartFromSession();
            return JsonResponseBuilder.buildSuccessResponse(cart);
        }
        return JsonResponseBuilder.buildErrorResponse("服务器出现错误");
    }

    @RequestMapping(value = "/clear/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> clear() {
        return null;
    }

    @RequestMapping(value = "/changeQuantity/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeCartItemQuantity(String productId, Integer quantity) throws Exception {
        ICartService cartService = new CartService(this.userSession);
        boolean flag = cartService.updateCartItem(productId, quantity);
        if (flag) {
            Cart cart = this.getCartFromSession();
            return JsonResponseBuilder.buildSuccessResponse(cart);
        }
        return JsonResponseBuilder.buildErrorResponse("服务器出现错误");
    }
}
