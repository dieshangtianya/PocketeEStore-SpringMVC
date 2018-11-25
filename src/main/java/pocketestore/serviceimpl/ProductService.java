package pocketestore.serviceimpl;

import org.springframework.web.multipart.MultipartFile;
import pocketestore.dao.IProductDao;
import pocketestore.infrastructure.exceptions.BusinessException;
import pocketestore.model.PaginationData;
import pocketestore.model.Product;
import pocketestore.model.query.ProductQuery;
import pocketestore.service.IProductService;
import pocketestore.serviceimpl.factory.DaoFactory;
import pocketestore.utils.BizResourceHelper;

import java.io.File;
import java.util.UUID;

public class ProductService implements IProductService {

    private int productImageMaxSize = 1024 * 1024 * 10;

    private IProductDao productDao;

    public ProductService() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        productDao = (IProductDao) daoFactory.createDao("ProductDaoImpl");
    }

    @Override
    public PaginationData<Product> getProducts(int pageIndex, int limit, ProductQuery query) {
        try {
            return productDao.queryProducts(pageIndex, limit, query);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new PaginationData<>();
        }
    }

    @Override
    public boolean addProduct(Product product) throws Exception {
        if (product == null) {
            return false;
        }

        MultipartFile thumbnailFile = product.getThumbnailFile();
        if (thumbnailFile != null) {
            // 保存产品缩略图
            String fileName = saveProductFileImage(thumbnailFile);
            product.setThumbnail(fileName);
        }
        return productDao.add(product);
    }

    @Override
    public boolean updateProduct(Product product) throws Exception {
        if (product == null) {
            throw new BusinessException("要更新的商品不存在");
        }

        MultipartFile thumbnailFile = product.getThumbnailFile();
        if (thumbnailFile != null) {
            boolean isDelete = BizResourceHelper.deleteProductImage(product.getThumbnail());
            if (!isDelete) {
                return false;
            }
            // 保存产品缩略图
            String fileName = saveProductFileImage(thumbnailFile);
            product.setThumbnail(fileName);
        }
        return productDao.update(product);
    }

    @Override
    public Product getProductById(String productId) {
        try {
            return productDao.getById(productId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteProduct(String productId) throws Exception {
        Product product = productDao.getById(productId);
        if (product == null) {
            throw new BusinessException("要删除的商品不存在！");
        }

        //删除商品信息
        boolean flag = productDao.remove(productId);
        if (!flag) {
            return false;
        }
        //删除商品图片，若商品删除成功，但是商品图片没有删除成功，以后运行一个task解决。
        BizResourceHelper.deleteProductImage(product.getThumbnail());

        return true;
    }

    private String saveProductFileImage(MultipartFile uploadFile) throws Exception {
        if (uploadFile.getSize() > this.productImageMaxSize) {
            throw new BusinessException("图片大小不能大于10M");
        }
        String targetFolderString = BizResourceHelper.getProductImagePath();
        String originalFilename = uploadFile.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = targetFolderString + "/" + UUID.randomUUID().toString() + extensionName;

        File productImageFile = new File(fileName);
        uploadFile.transferTo(productImageFile);
        return fileName.substring(BizResourceHelper.getWebRootPath().length() - 1);
    }

}
