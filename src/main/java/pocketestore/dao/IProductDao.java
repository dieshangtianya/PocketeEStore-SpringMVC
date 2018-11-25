package pocketestore.dao;

import pocketestore.model.PaginationData;
import pocketestore.model.Product;
import pocketestore.model.query.ProductQuery;

public interface IProductDao extends IEntityDao<Product> {
    PaginationData<Product> queryProducts(int pageIndex, int pageSize, ProductQuery queryObject) throws Exception;
}
