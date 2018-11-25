package pocketestore.service;

import pocketestore.model.PaginationData;
import pocketestore.model.Role;

import java.util.List;

public interface IRoleService {

    List<Role> getAllRoles();

    PaginationData<Role> getRoles(int pageIndex, int pageSize);
}
