package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pocketestore.controller.BaseController;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.infrastructure.exceptions.BusinessException;
import pocketestore.service.IOrderService;
import pocketestore.serviceimpl.OrderService;
import pocketestore.model.*;
import pocketestore.vo.*;

import java.util.Map;

@Controller("APIOrderController")
public class OrderController extends BaseController {

    /**
     * 创建订单
     *
     * @param cartVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/order/create/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createOrder(@RequestBody CartVO cartVO) throws Exception {
        if (cartVO == null) {
            throw new BusinessException("提交的信息有误");
        }
        IOrderService orderService = new OrderService(this.userSession);
        Cart orderCart = new Cart();
        for (CartItemVO cartItemVO : cartVO.getCartItems()) {
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(cartItemVO.getQuantity());
            Product product = new Product();
            product.setId(cartItemVO.getProductId());
            product.setPrice(cartItemVO.getPrice());
            cartItem.setProduct(product);
            orderCart.addCartItem(cartItem);
        }
        if (orderCart.getTotalPrice().compareTo(cartVO.getTotalPrice()) != 0) {
            throw new BusinessException("价格不一致，请重新刷新提交");
        }

        Order newOrder = orderService.placeOrder(orderCart);
        if (newOrder != null) {
            return JsonResponseBuilder.buildSuccessResponse(newOrder.getOrderNo());
        }
        return JsonResponseBuilder.buildErrorResponse("下单失败");
    }

    @RequestMapping(value="/api/management/order/list/v1",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(@RequestBody Map<String, Object> map)throws Exception{
        String pageStr = map.get("page").toString();
        String limitStr = map.get("limit").toString();
        int page = Integer.parseInt(pageStr);
        int limit = Integer.parseInt(limitStr);

        IOrderService orderService = new OrderService(this.userSession);
        PaginationData<Order> paginationData = orderService.getOrderList(page, limit);
        return JsonResponseBuilder.buildSuccessResponse(page, limit, paginationData.getTotal(), paginationData.getPageData());
    }
}