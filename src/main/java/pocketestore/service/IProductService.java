package pocketestore.service;

import pocketestore.model.PaginationData;
import pocketestore.model.Product;
import pocketestore.model.query.ProductQuery;

public interface IProductService {
    PaginationData<Product> getProducts(int pageIndex, int limits, ProductQuery queryObject);

    boolean addProduct(Product product) throws Exception;

    boolean updateProduct(Product product) throws Exception;

    Product getProductById(String productId);

    boolean deleteProduct(String productId) throws Exception;
}
