package pocketestore.daoimpl;

import pocketestore.dao.ICustomerDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.Customer;
import pocketestore.model.PaginationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDaoImpl implements ICustomerDao {

    public CustomerDaoImpl() {

    }

    @Override
    public List<Customer> getAll() throws Exception {
        String commandSql = "select * from t_Customer";

        List<Customer> list = new ArrayList<Customer>();

        List<Map<String, Object>> dataList = MySqlHelper.executeResultSet(CommandType.Text, commandSql);
        for (Map<String, Object> mapItem : dataList) {
            Customer customer = getCustomerFromMap(mapItem);
            list.add(customer);
        }
        return list;

    }

    @Override
    public PaginationData<Customer> get(int pageIndex, int pageSize) throws Exception {
        return null;
    }

    @Override
    public Customer getById(String id) throws Exception {
        String commandSql = "SELECT * FROM t_Customer WHERE Id = ?";
        List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, id);
        if (mapList != null && mapList.size() > 0) {
            Map<String, Object> mapItem = mapList.get(0);
            Customer customer = getCustomerFromMap(mapItem);
            return customer;
        }
        return null;
    }

    @Override
    public Customer getByUserNameAndPWD(String customerName, String pwd) throws Exception {
        String commandSql = "SELECT * FROM t_Customer WHERE CustomerName= ? AND Password= ? ";
        List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, customerName, pwd);
        if (mapList != null && mapList.size() > 0) {
            Map<String, Object> mapItem = mapList.get(0);
            Customer customer = getCustomerFromMap(mapItem);
            return customer;
        }
        return null;
    }

    @Override
    public boolean add(Customer entity) throws Exception {
        String commandSql = "INSERT INTO t_Customer(Id,CustomerName,Password,Sex,Email,Phone,State)VALUES(UUID(),?,?,?,?,?,?)";
        int rowAmount = MySqlHelper.executeNonQuery(
                CommandType.Text,
                commandSql,
                entity.getCustomerName(),
                entity.getPassword(),
                entity.getSex(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getState()
        );
        if (rowAmount == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Customer entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }

    private Customer getCustomerFromMap(Map<String, Object> mapItem) throws Exception {
        Customer customer = null;
        try {
            customer = new Customer();
            customer.setId(mapItem.get("Id").toString());
            customer.setCustomerName(mapItem.get("CustomerName").toString());
            customer.setEmail(mapItem.get("Email").toString());
            customer.setPhone(mapItem.get("Phone").toString());
            customer.setSex(mapItem.get("Sex").toString());
            customer.setState(mapItem.get("State").toString());
        } catch (Exception ex) {
            throw ex;
        }

        return customer;
    }
}
