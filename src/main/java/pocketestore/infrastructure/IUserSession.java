package pocketestore.infrastructure;

import pocketestore.model.Cart;

public interface IUserSession {
    boolean checkCustomerLogin();

    Object getDataFromSession(String key);

    void addDataToSession(String key, Object object);

    void removeDataFromSession(String key);

    String getAdminIdFromSession();

    String getCustomerIdFromSession();

    Cart getCartFromSession();

}
