package pocketestore.serviceimpl;

import pocketestore.dao.ICategorySetDao;
import pocketestore.model.CategorySet;
import pocketestore.service.ICategorySetService;
import pocketestore.serviceimpl.factory.DaoFactory;

import java.util.ArrayList;
import java.util.List;

public class CategorySetService implements ICategorySetService {
    private ICategorySetDao categorySetDao = null;

    public CategorySetService() {
        categorySetDao = (ICategorySetDao) DaoFactory.getInstance().createDao("CategorySetImpl");
    }

    public List<CategorySet> getAllStandCategorySets() {
        try{
            return categorySetDao.getAll();
        }catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<CategorySet>();
        }
    }
}
