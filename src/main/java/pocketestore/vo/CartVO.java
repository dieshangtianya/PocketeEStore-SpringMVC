package pocketestore.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {
    private BigDecimal totalPrice;

    private List<CartItemVO> cartItems;


    public List<CartItemVO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemVO> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
