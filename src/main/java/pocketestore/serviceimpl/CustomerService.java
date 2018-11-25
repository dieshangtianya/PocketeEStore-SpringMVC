package pocketestore.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import pocketestore.dao.ICustomerDao;
import pocketestore.service.ICustomerService;
import pocketestore.serviceimpl.factory.DaoFactory;
import pocketestore.model.Customer;
import pocketestore.utils.EncodeHelper;


public class CustomerService implements ICustomerService {

    private ICustomerDao customerDao;

    public CustomerService() {
        DaoFactory factory = DaoFactory.getInstance();
        customerDao = (ICustomerDao) factory.createDao("CustomerDaoImpl");
    }

    public List<Customer> getAllCustomer() {
        List<Customer> userList = new ArrayList<Customer>();
        try {
            userList = customerDao.getAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public Customer getUserByNameAndPassword(String userName, String password) {
        String md5Pwd = EncodeHelper.encodeWithMD5(password);
        Customer customer = null;
        try {
            customer = customerDao.getByUserNameAndPWD(userName, md5Pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        try {
            customer = customerDao.getById(customerId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public boolean registerCustomer(Customer customer) {
        String password = customer.getPassword();//加密密码
        String encodePassword = EncodeHelper.encodeWithMD5(password);
        customer.setPassword(encodePassword);
        customer.setSex("1");
        customer.setState("1");
        try{
           return customerDao.add(customer);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
