package pocketestore.service;

import pocketestore.model.Cart;
import pocketestore.model.CartItem;

import java.util.List;

public interface ICartService {
    boolean addToCart(String productId, int quantity) throws Exception;

    boolean removeFromCart(String productId) throws Exception;

    boolean updateCartItem(String productId,int quantity) throws Exception;

    boolean saveCart(String customerId, Cart cart) throws Exception;

    boolean deleteCart(String cartId) throws Exception;

    Cart syncCartData(List<CartItem> cartItems) throws Exception;

    Cart getCartByCustomerId(String customerId) throws Exception;
}
