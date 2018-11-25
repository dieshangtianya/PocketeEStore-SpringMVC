package pocketestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pocketestore.config.StaticData;
import pocketestore.infrastructure.IUserSession;
import pocketestore.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSession implements IUserSession {

    @Autowired
    private HttpServletRequest currentRequest;

    @Override
    public boolean checkCustomerLogin() {
        String customerId = getCustomerIdFromSession();
        if (customerId == null || "".equals(customerId)) {
            return false;
        }
        return true;
    }

    @Override
    public Object getDataFromSession(String key) {
        HttpSession session = currentRequest.getSession();
        Object object = session.getAttribute(key);
        return object;
    }

    @Override
    public void addDataToSession(String key, Object object) {
        HttpSession session = currentRequest.getSession();
        session.setAttribute(key, object);
    }

    @Override
    public void removeDataFromSession(String key) {
        HttpSession session = currentRequest.getSession();
        session.removeAttribute(key);
    }

    @Override
    public String getAdminIdFromSession() {
        String adminId = null;
        Object adminIdObject = this.getDataFromSession(StaticData.KEY_SESSION_ADMIN_ID);
        if (adminIdObject != null) {
            adminId = adminIdObject.toString();
        }
        return adminId;
    }

    @Override
    public String getCustomerIdFromSession() {
        String customerId = null;
        Object customerIdObject = this.getDataFromSession(StaticData.KEY_SESSION_CUSTOMER_ID);
        if (customerIdObject != null) {
            customerId = customerIdObject.toString();
        }
        return customerId;
    }

    @Override
    public Cart getCartFromSession() {
        Cart cartInfo = null;
        Object cartObject = this.getDataFromSession(StaticData.Key_SESSION_CART);
        if (cartObject != null) {
            cartInfo = (Cart) cartObject;
        }
        return cartInfo;
    }
}
