package pocketestore.service;

import pocketestore.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer();

    Customer getUserByNameAndPassword(String userName, String password);

    Customer getCustomerById(String customerId);

    boolean registerCustomer(Customer customer);
}
