package pocketestore.service;

import pocketestore.model.PaginationData;
import pocketestore.model.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAllRoles();

    PaginationData<Role> getRoles(int pageIndex, int pageSize);

    boolean addRole(Role role) throws Exception;

    boolean updateRole(Role role) throws Exception;

    boolean deleteRole(String roleId) throws Exception;
}
