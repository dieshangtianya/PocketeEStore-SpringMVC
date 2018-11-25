package pocketestore.daoimpl;

import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;
import org.apache.commons.lang3.StringUtils;
import pocketestore.dao.IResourceDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.PaginationData;
import pocketestore.model.Resource;
import pocketestore.model.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceDaoImpl implements IResourceDao {
    public ResourceDaoImpl() {

    }

    @Override
    public List<Resource> getResourcesByRoleIdArray(String[] roleIdArray) throws Exception {
        String commandSql = "SELECT A.* FROM t_Resource A LEFT JOIN t_Role_Resource B ON A.Id=B.ResourceId Where B.RoleId in (?) AND A.State=1";
        String inClause = StringUtils.join(roleIdArray, ',');
        List<Resource> resourceList = new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, inClause);
            resourceList = parseMapListToResourceList(mapList);
        } catch (Exception e) {
            throw e;
        }
        return resourceList;
    }

    @Override
    public List<Resource> getResourcesByRoleId(String roleId) throws Exception {
        String commandSql = "SELECT A.* FROM t_Resource A LEFT JOIN t_Role_Resource B ON A.Id=B.ResourceId Where B.RoleId=? AND State=1";
        List<Resource> resourceList = new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, roleId);
            resourceList = parseMapListToResourceList(mapList);
        } catch (Exception e) {
            throw e;
        }
        return resourceList;
    }

    @Override
    public List<Resource> getAll() throws Exception {
        String commandSql = "SELECT A.* FROM t_Resource A LEFT JOIN t_Role_Resource B ON A.Id=B.ResourceId AND State=1";
        List<Resource> resourceList = new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql);
            resourceList = parseMapListToResourceList(mapList);
        } catch (Exception e) {
            throw e;
        }
        return resourceList;
    }

    @Override
    public PaginationData<Resource> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_resource limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_resource";
        PaginationData<Resource> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex-1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Resource> resourceList = parseMapListToResourceList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(resourceList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }

    @Override
    public Resource getById(String id) throws Exception {
        return null;
    }

    @Override
    public boolean add(Resource entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Resource entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }

    private List<Resource> parseMapListToResourceList(List<Map<String, Object>> mapList) throws Exception {
        List<Resource> resourceList = new ArrayList<>();
        try {
            for (Map<String, Object> mapItem : mapList) {
                Resource resource = getResourceFromMap(mapItem);
                resourceList.add(resource);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return resourceList;
    }

    private Resource getResourceFromMap(Map<String, Object> mapItem) throws Exception {
        Resource resource = new Resource();
        try {
            resource.setId(mapItem.get("Id").toString());
            resource.setResourceName(mapItem.get("ResourceName").toString());

            Object parentResourceId = mapItem.get("ParentResourceId");
            if (parentResourceId != null) {
                resource.setParentResourceId(parentResourceId.toString());
            }

            Object resourceTypeObject = mapItem.get("ResourceType");
            ResourceType resourceType = ResourceType.values()[(int) resourceTypeObject];
            resource.setResourceType(resourceType);

            resource.setPath(mapItem.get("Path").toString());

            resource.setState(mapItem.get("State").toString());

            Object description = mapItem.get("Description");
            if (description != null) {
                resource.setDescription(description.toString());
            }
        } catch (Exception e) {
            throw e;
        }
        return resource;
    }
}
