package pocketestore.service;

import pocketestore.model.Admin;
import pocketestore.model.PaginationData;
import pocketestore.model.Resource;

import java.util.List;

public interface IAdminService {

    Admin getAdminByNameAndPassword(String userName, String password);

    Admin getAdminById(String adminId);

    PaginationData<Admin> getAdmins(int pageIndex, int pageSize);
}
