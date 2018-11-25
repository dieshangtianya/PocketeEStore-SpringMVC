package pocketestore.serviceimpl;

import pocketestore.config.StaticData;
import pocketestore.dao.IOrderDao;
import pocketestore.infrastructure.IUserSession;
import pocketestore.model.*;
import pocketestore.service.ICartService;
import pocketestore.service.IOrderService;
import pocketestore.serviceimpl.factory.DaoFactory;
import pocketestore.utils.SerialNumber;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderService implements IOrderService {

    private IUserSession userSession;
    private IOrderDao orderDao;
    private ICartService cartService;

    public OrderService(IUserSession userSession) {
        this.userSession = userSession;
        DaoFactory factory = DaoFactory.getInstance();
        this.orderDao = (IOrderDao) factory.createDao("OrderDaoImpl");
        this.cartService=new CartService(userSession);
    }

    @Override
    public Order placeOrder(Cart orderCart) throws Exception {
        //目前先不做验证
        Order order = new Order();
        String customerId = this.userSession.getCustomerIdFromSession();
        order.setCustomerId(customerId);
        order.setPayment(orderCart.getTotalPrice());
        Date now = new Date();
        order.setCreatedDate(now);
        order.setUpdatedDate(now);
        String orderNo = SerialNumber.newInstance(StaticData.ORDER_NUMBER_PREFIX).toString();
        order.setOrderNo(orderNo);
        order.setState(OrderState.Paid);
        List<OrderItem> orderItemList=order.getItems();
        for (CartItem cartItem : orderCart.getAllItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSkuId(cartItem.getProduct().getId());
            orderItem.setSubtotal(orderItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            orderItemList.add(orderItem);
        }

        boolean flag = orderDao.add(order);

        if (flag) {
            for(OrderItem item:order.getItems()){
                cartService.removeFromCart(item.getSkuId());
            }
            return order;
        }

        return null;

    }

    @Override
    public PaginationData<Order> getOrderList(int pageIndex, int limit) throws Exception {
        try {
            return orderDao.get(pageIndex, limit);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new PaginationData<>();
        }
    }
}
