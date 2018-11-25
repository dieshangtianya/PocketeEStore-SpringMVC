package pocketestore.serviceimpl;

import pocketestore.dao.IRoleDao;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;
import pocketestore.service.IRoleService;
import pocketestore.serviceimpl.factory.DaoFactory;

import java.util.ArrayList;
import java.util.List;

public class RoleService implements IRoleService {

    private IRoleDao roleDao;

    public RoleService() {
        roleDao = (IRoleDao) DaoFactory.getInstance().createDao("RoleDaoImpl");
    }

    @Override
    public List<Role> getAllRoles() {
        try {
            return roleDao.getAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public PaginationData<Role> getRoles(int pageIndex, int pageSize) {
        try {
            return roleDao.get(pageIndex, pageSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new PaginationData<>();
        }
    }
}
