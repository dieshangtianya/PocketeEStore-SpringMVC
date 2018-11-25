package pocketestore.daoimpl;

import pocketestore.dao.IAdminDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.Admin;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class AdminDaoImpl implements IAdminDao {
    public AdminDaoImpl() {

    }

    @Override
    public Admin getByAdminNameAndPWD(String customerName, String pwd) throws Exception {
        String commandSql = "SELECT * FROM t_Admin WHERE adminName= ? AND Password= ? ";
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, customerName, pwd);
            if (mapList != null && mapList.size() > 0) {
                Map<String, Object> mapItem = mapList.get(0);
                Admin admin = getAdminFromMap(mapItem);
                return admin;
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Admin> getAll() {
        return null;
    }

    @Override
    public PaginationData<Admin> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_Admin limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Admin";
        PaginationData<Admin> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex-1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Admin> resourceList = parseMapListToAdminList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(resourceList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }

    @Override
    public Admin getById(String id) throws Exception {
        String commandSql = "SELECT * FROM t_Admin WHERE Id = ?";
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, id);
            if (mapList != null && mapList.size() > 0) {
                Map<String, Object> mapItem = mapList.get(0);
                Admin admin = getAdminFromMap(mapItem);
                return admin;
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean add(Admin entity) {
        return false;
    }

    @Override
    public boolean update(Admin entity) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    private List<Admin> parseMapListToAdminList(List<Map<String, Object>> mapList) throws Exception {
        List<Admin> adminList = new ArrayList<>();
        try {
            for (Map<String, Object> mapItem : mapList) {
                Admin resource = getAdminFromMap(mapItem);
                adminList.add(resource);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return adminList;
    }

    private Admin getAdminFromMap(Map<String, Object> mapItem) throws Exception {
        Admin admin = new Admin();
        try {
            admin.setId(mapItem.get("Id").toString());
            admin.setAdminName(mapItem.get("AdminName").toString());

            Object email = mapItem.get("Email");
            if (email != null) {
                admin.setEmail(email.toString());
            }
            Object phone = mapItem.get("Phone");
            if (phone != null) {
                admin.setPhone(phone.toString());
            }

            admin.setState(mapItem.get("State").toString());
            Object sex = mapItem.get("Sex");
            if (sex != null) {
                admin.setSex(mapItem.get("Sex").toString());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            Date createdDate = sdf.parse(mapItem.get("CreatedDate").toString());
            admin.setCreatedDate(createdDate);
        } catch (Exception ex) {
            throw ex;
        }
        return admin;
    }
}
