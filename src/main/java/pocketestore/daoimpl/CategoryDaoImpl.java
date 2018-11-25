package pocketestore.daoimpl;

import pocketestore.dao.ICategoryDao;
import pocketestore.model.Category;
import pocketestore.model.PaginationData;

import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {
    public CategoryDaoImpl(){

    }

    @Override
    public List<Category> getAll() throws Exception {
        return null;
    }

    @Override
    public PaginationData<Category> get(int pageIndex, int pageSize) throws Exception {
        return null;
    }

    @Override
    public Category getById(String id) throws Exception {
        return null;
    }

    @Override
    public boolean add(Category entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Category entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }
}
