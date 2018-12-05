package pocketestore.daoimpl;

import pocketestore.dao.IRoleDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;
import pocketestore.model.State;

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
        String commandSql = "SELECT * FROM t_Role WHERE Id in(SELECT RoleId FROM t_Admin_Role WHERE AdminId=?)";
        List<Role> adminRoles = new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, adminId);
            if (mapList != null && mapList.size() > 0) {
                for (Map<String, Object> mapItem : mapList) {
                    Role role = getRoleFromMap(mapItem);
                    adminRoles.add(role);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return adminRoles;
    }

    @Override
    public Role getRoleByName(String roleName) throws Exception {
        String commandSql = "SELECT  * FROM t_Role where RoleName=?";

        List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, roleName);
        if (mapList != null && mapList.size() > 0) {
            Map<String, Object> mapItem = mapList.get(0);
            Role role = getRoleFromMap(mapItem);
            return role;
        }
        return null;
    }

    @Override
    public List<Role> getAll() throws Exception {
        return null;
    }

    @Override
    public PaginationData<Role> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_Role ORDER BY CreatedDate ASC limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Role";
        PaginationData<Role> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex - 1) * pageSize;
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
        String commandSql = "SELECT  * FROM t_Role where Id=?";

        List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, id);
        if (mapList != null && mapList.size() > 0) {
            Map<String, Object> mapItem = mapList.get(0);
            Role role = getRoleFromMap(mapItem);
            return role;
        }
        return null;
    }

    @Override
    public boolean add(Role entity) throws Exception {
        String commandSql = "INSERT INTO t_role (Id,RoleName,State) VALUES(uuid(),?,?)";
        int state = entity.getState().getValue();
        int rowCount = MySqlHelper.executeNonQuery(CommandType.Text, commandSql, entity.getRoleName(), state);
        return rowCount > 0;
    }

    @Override
    public boolean update(Role entity) throws Exception {
        String commandSql = "Update t_role SET RoleName=?,State=? WHERE Id=?";
        int state = entity.getState().getValue();
        int rowCount = MySqlHelper.executeNonQuery(CommandType.Text, commandSql, entity.getRoleName(), state, entity.getId());
        return rowCount > 0;
    }

    @Override
    public boolean remove(String id) throws Exception {
        String commandSql = "DELETE FROM t_role WHERE Id=?";
        int rowCount = MySqlHelper.executeNonQuery(CommandType.Text, commandSql, id);
        return rowCount > 0;
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

    private Role getRoleFromMap(Map<String, Object> mapItem) throws Exception {
        Role role = new Role();
        try {

            role.setId(mapItem.get("Id").toString());
            role.setRoleName(mapItem.get("RoleName").toString());

            Integer stateValue = Integer.parseInt(mapItem.get("State").toString());
            if (stateValue != null) {
                role.setState(State.fromInt(stateValue));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date createdDate = sdf.parse(mapItem.get("CreatedDate").toString());
            role.setCreatedDate(createdDate);
        } catch (Exception ex) {
            throw ex;
        }
        return role;
    }
}
