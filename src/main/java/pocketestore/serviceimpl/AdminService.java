package pocketestore.serviceimpl;

import pocketestore.dao.IAdminDao;
import pocketestore.dao.IRoleDao;
import pocketestore.infrastructure.exceptions.BusinessException;
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
        roleDao = (IRoleDao) daoFactory.createDao("RoleDaoImpl");
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
            List<Role> roleList = roleDao.getAdminRoles(adminId);
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

    @Override
    public boolean addAdmin(Admin admin) throws Exception {
        Admin existAdmin = adminDao.getByAdminName(admin.getAdminName());
        if (existAdmin != null) {
            throw new BusinessException("已存在用户名为" + admin.getAdminName() + "的用户");
        }
        String md5Pwd = getMD5PasswordFromBase64(admin.getPassword());
        admin.setPassword(md5Pwd);
        return adminDao.add(admin);
    }

    @Override
    public boolean deleteAdmin(String adminId) throws Exception {
        Admin admin = adminDao.getById(adminId);
        if (admin == null) {
            throw new BusinessException("要删除的管理员不存在");
        }
        return adminDao.remove(adminId);
    }

    @Override
    public boolean updateAdmin(Admin admin) throws Exception {
        Admin existAdmin = adminDao.getByAdminName(admin.getAdminName());
        if (existAdmin != null && !existAdmin.getId().equals(admin.getId())){
            throw new BusinessException("已存在用户名为" + admin.getAdminName() + "的用户");
        }
        if (!admin.getPassword().equals(existAdmin.getPassword())) {
            String md5Pwd = getMD5PasswordFromBase64(admin.getPassword());
            admin.setPassword(md5Pwd);
        }
        return adminDao.update(admin);
    }

    private String getMD5PasswordFromBase64(String base64Password) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        String originalPassword = new String(decoder.decode(base64Password), "UTF-8");
        String md5Password = EncodeHelper.encodeWithMD5(originalPassword);
        return md5Password;
    }
}
