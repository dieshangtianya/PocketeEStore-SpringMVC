package pocketestore.daoimpl;

import pocketestore.dao.ICategorySetDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.*;
import pocketestore.utils.DataGroupUtils;
import pocketestore.utils.IDataGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategorySetImpl implements ICategorySetDao {


    @Override
    public List<CategorySet> getAll() throws Exception {

        StringBuffer buffer=new StringBuffer();
        buffer.append("SELECT t_CategorySet.Id as CategorySetId,SetName,OrderNum,t_Category_Set_Link.CategoryId,CategoryName ");
        buffer.append(" FROM t_CategorySet ");
        buffer.append(" LEFT JOIN t_Category_Set_Link ON t_CategorySet.Id=t_Category_Set_Link.SetId ");
        buffer.append(" LEFT JOIN t_Category ON t_Category.Id=t_Category_Set_Link.CategoryId");
        buffer.append(" WHERE SetType='1' and CategoryName IS NOT NULL AND t_Category.ParentId IS NULL ");
        buffer.append("  AND t_Category.State='1'");

        String commandSql = buffer.toString();

        List<Map<String, Object>> dataList = MySqlHelper.executeResultSet(CommandType.Text, commandSql);

        // 这里由于对java的lambda和一些集合操作的不熟悉，就用一个比较笨的方法来实现
        // 等以后学习了java8的相关特性进行修改
        IDataGroup<Map<String, Object>> dataGroupAccessor = new IDataGroup<Map<String, Object>>() {
            @Override
            public String getGroupProperty(Map<String, Object> item) {
                return item.get("CategorySetId").toString();
            }
        };
        Map<String, List<Map<String, Object>>> groupResult = DataGroupUtils.groupListMapData(dataList, dataGroupAccessor);

        List<CategorySet> categorySetList = new ArrayList<CategorySet>();

        for (String key : groupResult.keySet()) {
            CategorySet categorySetItem = new CategorySet(key);
            List<Map<String, Object>> categoryList = groupResult.get(key);
            for (Map<String, Object> categoryMapItem : categoryList) {
                Category categoryItem = new Category();
                categoryItem.setId(categoryMapItem.get("CategoryId").toString());
                categoryItem.setCategoryName(categoryMapItem.get("CategoryName").toString());
                if (categorySetItem.getCategorySetName() == null) {
                    categorySetItem.setCategorySetName(categoryMapItem.get("SetName").toString());
                }
                categorySetItem.getCategoryList().add(categoryItem);
            }
            categorySetList.add(categorySetItem);
        }

        return categorySetList;
    }

    @Override
    public PaginationData<CategorySet> get(int pageIndex, int pageSize) throws Exception {
        return null;
    }

    @Override
    public CategorySet getById(String id) throws Exception {
        return null;
    }

    @Override
    public boolean add(CategorySet entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(CategorySet entity) throws Exception {
        return false;
    }

    @Override
    public boolean remove(String id) throws Exception {
        return false;
    }
}
