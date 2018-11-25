package pocketestore.serviceimpl;


import pocketestore.config.StaticData;
import pocketestore.infrastructure.IUserSession;
import pocketestore.infrastructure.exceptions.BusinessException;
import pocketestore.model.Cart;
import pocketestore.model.CartItem;
import pocketestore.model.Product;
import pocketestore.service.ICartService;
import pocketestore.service.IProductService;

import java.util.List;

/**
 * CartService是购物车业务处理类，其主要涉及了购物车的基本操作。
 * 业务规则：
 * (1)客户不登录可以向购物车添加、删除商品
 * (2)修改购物车某项商品的记录
 * (3)客户如果登录，其购物车信息需要保存到数据库，在其他地方登录也能获取购物车信息
 * (4)浏览器会保存购物车信息到local storage，未登录前也可查看到相关信息
 */
public class CartService implements ICartService {
    private IUserSession userSession;

    public CartService(IUserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public boolean addToCart(String productId, int quantity) {
        IProductService productService = new ProductService();
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new BusinessException("不存在该产品");
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        Cart cart = this.userSession.getCartFromSession();
        cart.addCartItem(cartItem);

        return true;
    }

    @Override
    public boolean removeFromCart(String productId) {
        Cart cart = this.userSession.getCartFromSession();
        cart.removeCarItem(productId);
        return true;
    }

    @Override
    public boolean updateCartItem(String productId, int quantity) {
        Cart cart = this.userSession.getCartFromSession();
        CartItem cartItem = cart.getAllItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().get();
        cartItem.setQuantity(quantity);
        return true;
    }

    @Override
    public boolean saveCart(String customerId, Cart cart) {
        return false;
    }

    @Override
    public boolean deleteCart(String cartId) {
        return false;
    }

    @Override
    public Cart syncCartData(List<CartItem> cartItems) {
        Cart cart = this.userSession.getCartFromSession();
        if (!this.userSession.checkCustomerLogin()) {
            //如果用户未登录
            if (cart == null) {
                // 新建购物车
                cart = new Cart();
            }
        } else {
            //如果用户已经登录
            if (cart == null) {
                String customerId = this.userSession.getCustomerIdFromSession();
                cart = this.getCartByCustomerId(customerId);
            }
        }

        if (cartItems != null && cartItems.size() > 0) {
            for (CartItem cartItem : cartItems) {
                if (cart.existCartItem(cartItem.getProduct().getId())) {
                    cart.changeCartItemQuantity(cartItem);
                } else {
                    cart.addCartItem(cartItem);
                }
            }
        }

        //保存到Session中
        this.userSession.addDataToSession(StaticData.Key_SESSION_CART, cart);

        return cart;
    }

    @Override
    public Cart getCartByCustomerId(String customerId) {
        return null;
    }
}
