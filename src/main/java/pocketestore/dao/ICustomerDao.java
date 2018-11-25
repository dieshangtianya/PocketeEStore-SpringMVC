package pocketestore.dao;

import pocketestore.model.Customer;

public interface ICustomerDao extends IEntityDao<Customer> {
    Customer getByUserNameAndPWD(String customerName, String pwd) throws Exception;
}
