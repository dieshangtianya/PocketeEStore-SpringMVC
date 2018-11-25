package pocketestore.service;

import pocketestore.model.PaginationData;
import pocketestore.model.Resource;

import java.util.List;

public interface IResourceService {
    List<Resource> getMenuResourceByAdminId(String adminId);

    List<Resource> getAllResources();

    PaginationData<Resource> getResources(int pageIndex, int pageSize);
}
