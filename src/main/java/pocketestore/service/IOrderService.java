package pocketestore.service;

import pocketestore.model.Order;
import pocketestore.model.Cart;
import pocketestore.model.PaginationData;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Cart cart) throws Exception;
    PaginationData<Order> getOrderList(int pageIndex, int limit)throws  Exception;
}
