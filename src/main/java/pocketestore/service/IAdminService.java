package pocketestore.service;

import pocketestore.model.Admin;
import pocketestore.model.PaginationData;

public interface IAdminService {

    Admin getAdminByNameAndPassword(String userName, String password);

    Admin getAdminById(String adminId);

    PaginationData<Admin> getAdmins(int pageIndex, int pageSize);

    boolean addAdmin(Admin admin) throws Exception;

    boolean deleteAdmin(String adminId) throws Exception;

    boolean updateAdmin(Admin admin) throws Exception;
}
