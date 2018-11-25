package pocketestore.dao;

import pocketestore.model.Admin;
import pocketestore.model.Resource;
import pocketestore.model.Role;

import java.util.ArrayList;

public interface IAdminDao extends IEntityDao<Admin>{
    Admin getByAdminNameAndPWD(String customerName, String pwd) throws Exception;
}
