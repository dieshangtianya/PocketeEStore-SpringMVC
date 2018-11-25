package pocketestore.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class Cart extends EntityBase {
    private Map<String, CartItem> cartItemMap;

    public Cart() {
        cartItemMap = new HashMap<>();
    }

    /**
     * 添加一个产品项
     *
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem) {
        String productId = cartItem.getProduct().getId();
        if (cartItemMap.containsKey(productId)) {
            CartItem existedCartItem = cartItemMap.get(productId);
            int newQuantity = existedCartItem.getQuantity() + cartItem.getQuantity();
            existedCartItem.setQuantity(newQuantity);
        } else {
            cartItemMap.put(productId, cartItem);
        }
    }

    /**
     * 移除一个产品项
     *
     * @param productId
     */
    public void removeCarItem(String productId) {
        cartItemMap.remove(productId);
    }

    /**
     * 清空购物车
     */
    public void clear() {
        cartItemMap.clear();
    }

    /**
     * 修改购物车选项的数量
     *
     * @param cartItem
     */
    public void changeCartItemQuantity(CartItem cartItem) {
        String productId = cartItem.getProduct().getId();
        if (cartItemMap.containsKey(productId)) {
            cartItemMap.put(productId, cartItem);
        }
    }

    public CartItem get(String productId) {
        return cartItemMap.get(productId);
    }

    public boolean existCartItem(String productId) {
        return cartItemMap.containsKey(productId);
    }

    public List<CartItem> getAllItems() {
        return new ArrayList<>(cartItemMap.values());
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : this.cartItemMap.values()) {
            int quantity = cartItem.getQuantity();
            Product product = cartItem.getProduct();
            totalPrice = totalPrice.add(new BigDecimal(quantity).multiply(product.getPrice()));
        }
        return totalPrice;
    }
}
