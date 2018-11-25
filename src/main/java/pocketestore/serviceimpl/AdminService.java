package pocketestore.serviceimpl;

import pocketestore.dao.IAdminDao;
import pocketestore.dao.IRoleDao;
import pocketestore.model.Admin;
import pocketestore.model.PaginationData;
import pocketestore.model.Role;
import pocketestore.service.IAdminService;
import pocketestore.serviceimpl.factory.DaoFactory;
import pocketestore.utils.EncodeHelper;


import java.util.Base64;
import java.util.List;

public class AdminService implements IAdminService {

    private IAdminDao adminDao;
    private IRoleDao roleDao;

    public AdminService() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        adminDao = (IAdminDao) daoFactory.createDao("AdminDaoImpl");
        roleDao=(IRoleDao)daoFactory.createDao("RoleDaoImpl");
    }

    public Admin getAdminByNameAndPassword(String userName, String password) {
        final Base64.Decoder decoder = Base64.getDecoder();
        String purePassword = null;
        Admin admin = null;
        try {
            purePassword = new String(decoder.decode(password), "UTF-8");
            String md5Pwd = EncodeHelper.encodeWithMD5(purePassword);
            admin = adminDao.getByAdminNameAndPWD(userName, md5Pwd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return admin;
    }

    public Admin getAdminById(String adminId) {
        Admin admin = null;
        try {
            admin = adminDao.getById(adminId);
            List<Role> roleList=roleDao.getAdminRoles(adminId);
            admin.setRoles(roleList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return admin;
    }

    @Override
    public PaginationData<Admin> getAdmins(int pageIndex, int pageSize) {
        try {
            return adminDao.get(pageIndex, pageSize);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new PaginationData<>();
        }
    }
}
