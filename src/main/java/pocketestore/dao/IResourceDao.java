package pocketestore.dao;

import pocketestore.model.Resource;

import java.util.List;

public interface IResourceDao extends IEntityDao<Resource> {

    List<Resource> getResourcesByRoleIdArray(String[] roleIdArray) throws Exception;

    List<Resource> getResourcesByRoleId(String roleId) throws  Exception;
}
