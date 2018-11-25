package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.PaginationData;
import pocketestore.model.Product;
import pocketestore.model.query.ProductQuery;
import pocketestore.service.IProductService;
import pocketestore.serviceimpl.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Controller("APIProductController")
public class ProductController {
    @RequestMapping(value = {"/api/management/product/list/v1"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> productList(@RequestBody Map<String, Object> map) {
        String pageStr = map.get("page").toString();
        String limitStr = map.get("limit").toString();
        Object keywordObject = map.get("keyword");
        ProductQuery queryObject = new ProductQuery();
        if (keywordObject != null) {
            String keyword = keywordObject.toString();
            queryObject.setKeyword(keyword);
        }
        int page = Integer.parseInt(pageStr);
        int limit = Integer.parseInt(limitStr);
        IProductService productService = new ProductService();

        PaginationData<Product> paginationData = productService.getProducts(page, limit, queryObject);
        return JsonResponseBuilder.buildSuccessResponse(page, limit, paginationData.getTotal(), paginationData.getPageData());
    }

    @RequestMapping(value = {"/api/product/list/v1"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryList(@RequestBody Map<String, Object> map) {
        return productList(map);
    }

    @RequestMapping(value = "/api/management/product/add/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(@RequestParam("thumbnailFile") MultipartFile thumbnailFile, HttpServletRequest request) throws Exception {
        //关于文件上传需要有multipart解析器，这里用的是commons-fileupload，需要配置相关bean。否则无法接受相关参数
        Product productInfo = getProductFromRequest(thumbnailFile, request);
        IProductService productService = new ProductService();
        boolean flag = productService.addProduct(productInfo);

        if (!flag) {
            return JsonResponseBuilder.buildErrorResponse("商品保存出现错误，请尝试重试！");
        }
        return JsonResponseBuilder.buildSuccessResponse();
    }

    @RequestMapping(value = "/api/management/product/update/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestParam(required = false) MultipartFile thumbnailFile, HttpServletRequest request) throws Exception {
        Product productInfo = getProductFromRequest(thumbnailFile, request);
        IProductService productService = new ProductService();
        boolean flag = productService.updateProduct(productInfo);
        if (!flag) {
            return JsonResponseBuilder.buildErrorResponse("商品保存出现错误，请尝试重试！");
        }
        return JsonResponseBuilder.buildSuccessResponse();
    }

    @RequestMapping(value = "/api/management/product/delete/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam() String id) throws Exception {
        if ("".equals(id)) {
            return JsonResponseBuilder.buildErrorResponse("未提供商品编号");
        } else {
            IProductService productService = new ProductService();
            boolean flag = productService.deleteProduct(id);
            if (!flag) {
                return JsonResponseBuilder.buildErrorResponse("商品删除失败");
            }
            return JsonResponseBuilder.buildSuccessResponse();
        }
    }


    private Product getProductFromRequest(MultipartFile thumbnailFile, HttpServletRequest request) {
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String price = request.getParameter("price");
        String brandName = request.getParameter("brandName");
        String habitat = request.getParameter("habitat");
        String tags = request.getParameter("tags");
        String description = request.getParameter("description");
        String state = request.getParameter("state");

        Product productInfo = new Product();
        if (!"".equals(id)) {
            IProductService productService = new ProductService();
            productInfo=productService.getProductById(id);
        }
        productInfo.setProductName(productName);
        productInfo.setBrandName(brandName);
        productInfo.setPrice(new BigDecimal(price));
        productInfo.setHabitat(habitat);
        if (tags != null && !tags.equals("")) {
            productInfo.setTags(tags.split(","));
        }
        productInfo.setState(state);
        productInfo.setDescription(description);

        if (thumbnailFile != null) {
            productInfo.setThumbnailFile(thumbnailFile);
        }
        return productInfo;
    }
}
