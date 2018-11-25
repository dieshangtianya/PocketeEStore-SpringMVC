package pocketestore.serviceimpl;

import pocketestore.config.StaticData;
import pocketestore.dao.IResourceDao;
import pocketestore.model.*;
import pocketestore.service.*;
import pocketestore.service.IResourceService;
import pocketestore.serviceimpl.factory.DaoFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ResourceService implements IResourceService {

    private IResourceDao resourceDao;

    public ResourceService() {
        resourceDao = (IResourceDao) DaoFactory.getInstance().createDao("ResourceDaoImpl");
    }

    @Override
    public List<Resource> getMenuResourceByAdminId(String adminId) {
        IAdminService iadminService = new AdminService();
        Admin admin = iadminService.getAdminById(adminId);
        List<Role> roleList = admin.getRoles();
        List<Role> adminRoleResult = roleList.stream()
                .filter((Role role) -> role.getRoleName().equals(StaticData.ADMIN_ROLE_NAME))
                .collect(Collectors.toList());
        List<Resource> resourceList = new ArrayList<>();
        try {
            if (adminRoleResult.size() > 0) {
                // if the admin is Administrator role
                resourceList = resourceDao.getAll();

            } else {
                // it the admin is not Administrator role
                List<String> roleIdList = roleList.stream()
                        .map(roleItem -> {
                            return roleItem.getId();
                        }).collect(Collectors.toList());
                resourceList = resourceDao.getResourcesByRoleIdArray((String[]) roleIdList.toArray());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resourceList;

    }

    @Override
    public List<Resource> getAllResources() {
        try {
            return resourceDao.getAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public PaginationData<Resource> getResources(int pageIndex, int pageSize) {
        try {
            return resourceDao.get(pageIndex, pageSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new PaginationData<>();
        }

    }
}
