package pocketestore.serviceimpl;

import pocketestore.dao.IRoleDao;
import pocketestore.infrastructure.exceptions.BusinessException;
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

    @Override
    public boolean addRole(Role role) throws Exception {
        Role existedRole = roleDao.getRoleByName(role.getRoleName());
        if (existedRole != null) {
            throw new BusinessException("已存在相同名称的角色");
        }
        return roleDao.add(role);
    }

    @Override
    public boolean updateRole(Role role) throws Exception {
        Role existedRole = roleDao.getRoleByName(role.getRoleName());
        if (existedRole != null && !existedRole.getId().equals(role.getId())) {
            // 如果id不同，但是名称相同
            throw new BusinessException("已存在相同名称的角色");
        }
        return roleDao.update(role);
    }

    @Override
    public boolean deleteRole(String roleId) throws Exception {
        Role existedRole = roleDao.getById(roleId);
        if (existedRole == null) {
            throw new BusinessException("要删除的角色不存在");
        }
        if (existedRole.getRoleName().equals("Administrator")) {
            throw new BusinessException("管理员角色不允许删除");
        }
        // to do --删除角色关联的权限
        return roleDao.remove(roleId);
    }
}
