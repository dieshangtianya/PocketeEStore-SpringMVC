package pocketestore.daoimpl;

import org.apache.commons.lang3.StringUtils;
import pocketestore.dao.IProductDao;
import pocketestore.daoimpl.utils.CommandType;
import pocketestore.daoimpl.utils.MySqlHelper;
import pocketestore.model.PaginationData;
import pocketestore.model.Product;
import pocketestore.model.query.ProductQuery;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements IProductDao {
    public ProductDaoImpl() {

    }

    @Override
    public List<Product> getAll() throws Exception {
        return null;
    }

    @Override
    public PaginationData<Product> get(int pageIndex, int pageSize) throws Exception {
        String commandSql = "SELECT * FROM t_Product ORDER BY CreatedDate DESC limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Product";
        PaginationData<Product> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex - 1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Product> productList = parseMapListToProductList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(productList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }

    @Override
    public Product getById(String id) throws Exception {
        String commandSql = "SELECT * FROM t_Product WHERE id=?";
        try {
            List<Map<String, Object>> mapObjectList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, id);
            if (mapObjectList.size() > 0) {
                return getProductFromMap(mapObjectList.get(0));
            } else {
                return null;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean add(Product entity) throws Exception {
        String commandSql = "insert into t_Product(Id,ProductName,Brand,Price,Thumbnail,Habitat,Tags,State,Description) values(UUID(),?,?,?,?,?,?,?,?)";
        try {
            String[] tags = entity.getTags();
            String tagString = null;
            if (tags != null && tags.length > 0) {
                tagString = StringUtils.join(tags, ",");
            }
            int rowCount = MySqlHelper.executeNonQuery(
                    CommandType.Text,
                    commandSql,
                    entity.getProductName(),
                    entity.getBrandName(),
                    entity.getPrice(),
                    entity.getThumbnail(),
                    entity.getHabitat(),
                    tagString,
                    entity.getState(),
                    entity.getDescription());
            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean update(Product entity) throws Exception {
        String commandSql = "Update t_Product set ProductName=?,Brand=?,Price=?,Thumbnail=?,Habitat=?,Tags=?,State=?,Description=? WHERE Id=?";
        try {
            String[] tags = entity.getTags();
            String tagString = null;
            if (tags != null && tags.length > 0) {
                tagString = StringUtils.join(tags, ",");
            }
            int rowCount = MySqlHelper.executeNonQuery(
                    CommandType.Text,
                    commandSql,
                    entity.getProductName(),
                    entity.getBrandName(),
                    entity.getPrice(),
                    entity.getThumbnail(),
                    entity.getHabitat(),
                    tagString,
                    entity.getState(),
                    entity.getDescription(),
                    entity.getId());
            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean remove(String id) throws Exception {
        String commandSql = "DELETE FROM t_Product WHERE Id=?";
        try {
            int rowCount = MySqlHelper.executeNonQuery(
                    CommandType.Text,
                    commandSql,
                    id);
            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public PaginationData<Product> queryProducts(int pageIndex, int pageSize, ProductQuery queryObject) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (queryObject.getKeyword() != null && queryObject.getKeyword() != "") {
            sb.append(" AND (ProductName like '%" + queryObject.getKeyword() + "%' OR Tags like '%" + queryObject.getKeyword() + "%')");
        }
        String commandSql = "SELECT * FROM t_Product WHERE 1=1 " + sb.toString() + " ORDER BY CreatedDate DESC limit ?,?";
        String totalCommandSql = "SELECT COUNT(*) FROM t_Product WHERE 1=1 " + sb.toString() + "";
        PaginationData<Product> paginationData = new PaginationData<>();
        int offset = 0;
        if (pageIndex > 1) {
            offset = (pageIndex - 1) * pageSize;
        }
        try {
            List<Map<String, Object>> mapList = MySqlHelper.executeResultSet(CommandType.Text, commandSql, offset, pageSize);
            List<Product> productList = parseMapListToProductList(mapList);
            long totalSize = (long) MySqlHelper.executeScalar(CommandType.Text, totalCommandSql);
            paginationData.setPageData(productList);
            paginationData.setTotal(totalSize);
        } catch (Exception e) {
            throw e;
        }
        return paginationData;
    }


    private List<Product> parseMapListToProductList(List<Map<String, Object>> mapList) throws Exception {
        List<Product> productList = new ArrayList<>();
        try {
            for (Map<String, Object> mapItem : mapList) {
                Product productItem = getProductFromMap(mapItem);
                productList.add(productItem);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return productList;
    }

    private Product getProductFromMap(Map<String, Object> mapItem) throws Exception {
        Product product = new Product();
        try {
            product.setId(mapItem.get("Id").toString());
            product.setProductName(mapItem.get("ProductName").toString());
            product.setPrice(new BigDecimal(mapItem.get("Price").toString()));

            Object brandObject = mapItem.get("Brand");
            if (brandObject != null) {
                product.setBrandName(brandObject.toString());
            }

            Object tagObject = mapItem.get("Tags");
            String[] tags = new String[]{};
            if (tagObject != null) {
                tags = tagObject.toString().split(",");
            }
            product.setTags(tags);


            Object habitatObject = mapItem.get("Habitat");
            if (habitatObject != null) {
                product.setHabitat(habitatObject.toString());
            }

            product.setState(mapItem.get("State").toString());

            Object descriptionObject = mapItem.get("Description");
            if (descriptionObject != null) {
                product.setDescription(descriptionObject.toString());
            }

            Object thumbnailObject = mapItem.get("Thumbnail");
            if (thumbnailObject != null) {
                product.setThumbnail(thumbnailObject.toString());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date createdDate = sdf.parse(mapItem.get("CreatedDate").toString());
            product.setCreatedDate(createdDate);

        } catch (Exception ex) {
            throw ex;
        }
        return product;
    }
}
