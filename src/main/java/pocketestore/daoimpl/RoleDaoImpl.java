package pocketestore.daoimpl;

import pocketestore.dao.IRoleDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoleDaoImpl implements IRoleDao {
    public RoleDaoImpl() {

    }

    @Override
    public List<Role> getAdminRoles(String adminId) throws Exception {
        String commandSql="SELECT * FROM t_Role WHERE Id in(SELECT RoleId FROM t_Admin_Role WHERE AdminId=?)";
        List<Role> adminRoles=new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, adminId);
            if (mapList != null && mapList.size() > 0) {
               for(Map<String,Object> mapItem: mapList){
                   Role role=getRoleFromMap(mapItem);
                   adminRoles.add(role);
               }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return adminRoles;
    }

    @Override
    public List<Role> getAll() throws Exception {
        return null;
    }

    @Override
    public PaginationData<Role> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_Role limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Role";
        PaginationData<Role> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex-1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Role> roleList = parseMapListToResourceList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(roleList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }

    @Override
    public Role getById(String id) throws Exception {
        return null;
    }

    @Override
    public boolean add(Role entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Role entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }

    private List<Role> parseMapListToResourceList(List<Map<String, Object>> mapList) throws Exception {
        List<Role> resourceList = new ArrayList<>();
        try {
            for (Map<String, Object> mapItem : mapList) {
                Role resource = getRoleFromMap(mapItem);
                resourceList.add(resource);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return resourceList;
    }

    private Role getRoleFromMap(Map<String,Object> mapItem) throws  Exception{
        Role role=new Role();
        try{

            role.setId(mapItem.get("Id").toString());
            role.setRoleName(mapItem.get("RoleName").toString());
            role.setState(mapItem.get("State").toString());

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date createdDate = sdf.parse(mapItem.get("CreatedDate").toString());
            role.setCreatedDate(createdDate);
        }catch (Exception ex){
            throw ex;
        }
        return role;
    }
}
