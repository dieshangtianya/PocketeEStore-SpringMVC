package pocketestore.dao;

import pocketestore.model.Role;

import java.util.*;

public interface IRoleDao extends IEntityDao<Role> {
    List<Role> getAdminRoles(String adminId) throws Exception;

    Role getRoleByName(String roleName) throws Exception;
}
