package pocketestore.daoimpl;

import pocketestore.dao.IOrderDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.Order;
import pocketestore.model.OrderItem;
import pocketestore.model.OrderState;
import pocketestore.model.PaginationData;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderDaoImpl implements IOrderDao {
    @Override
    public List<Order> getAll() throws Exception {
        return null;
    }

    @Override
    public PaginationData<Order> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_Order ORDER BY UpdatedDate DESC limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Order";
        PaginationData<Order> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex - 1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Order> orderList = parseMapListToOrderList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(orderList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }

    @Override
    public Order getById(String id) throws Exception {
        return null;
    }

    @Override
    public boolean add(Order entity) throws Exception {
        String commandInsertOrderSql = "INSERT INTO t_Order(Id,OrderNo,CustomerId,State,Payment,UpdatedDate,CreatedDate) VALUES(?,?,?,?,?,?,?)";
        String commandInsertOrderItemSql = "INSERT INTO t_Order_Item(Id,OrderId,SkuId,Price,Quantity,Subtotal) VALUES (?,?,?,?,?,?)";
        Connection connection = MySqlHelper.getConnection(true);
        String orderUUID = UUID.randomUUID().toString();
        try {
            int rowCount = MySqlHelper.executeNonQuery(
                    connection,
                    CommandType.Text,
                    true,
                    commandInsertOrderSql,
                    orderUUID,
                    entity.getOrderNo(),
                    entity.getCustomerId(),
                    entity.getState().getValue(),
                    entity.getPayment(),
                    entity.getUpdatedDate(),
                    entity.getCreatedDate()
            );
            if (rowCount == 0) {
                throw new Exception("新增订单项失败");
            }

            for (OrderItem orderItem : entity.getItems()) {
                String itemUUID = UUID.randomUUID().toString();
                rowCount = MySqlHelper.executeNonQuery(
                        connection,
                        CommandType.Text,
                        true,
                        commandInsertOrderItemSql,
                        itemUUID,
                        orderUUID,
                        orderItem.getSkuId(),
                        orderItem.getPrice(),
                        orderItem.getQuantity(),
                        orderItem.getSubtotal()
                );
                if (rowCount == 0) {
                    throw new Exception("新增订单项失败");
                }
            }
            connection.commit();
            return true;
        } catch (Exception ex) {
            connection.rollback();
            return false;
        } finally {
            MySqlHelper.close(connection);
        }
    }

    @Override
    public boolean update(Order entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }

    private List<Order> parseMapListToOrderList(List<Map<String, Object>> mapList) throws Exception {
        List<Order> ordertList = new ArrayList<>();
        try {
            for (Map<String, Object> mapItem : mapList) {
                Order order = getOrderFromMap(mapItem);
                ordertList.add(order);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return ordertList;
    }

    private Order getOrderFromMap(Map<String, Object> mapItem) throws Exception {
        Order order = null;
        try {
            order = new Order();
            order.setId(mapItem.get("Id").toString());
            order.setOrderNo(mapItem.get("OrderNo").toString());
            order.setCustomerId(mapItem.get("CustomerId").toString());
            order.setPayment(new BigDecimal(mapItem.get("Payment").toString()));

            int orderStateValue = Integer.parseInt(mapItem.get("State").toString());
            OrderState orderState = OrderState.fromInt(orderStateValue);
            order.setState(orderState);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date createdDate = sdf.parse(mapItem.get("CreatedDate").toString());
            order.setCreatedDate(createdDate);

            Date updatedDate = sdf.parse(mapItem.get("UpdatedDate").toString());
            order.setUpdatedDate(updatedDate);

        } catch (Exception ex) {
            throw ex;
        }
        return order;
    }
}
