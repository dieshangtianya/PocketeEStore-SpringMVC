package pocketestore.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pocketestore.infrastructure.JsonResponseBuilder;
import pocketestore.model.PaginationData;
import pocketestore.model.Resource;
import pocketestore.service.IResourceService;
import pocketestore.serviceimpl.ResourceService;

import java.util.Map;

@Controller("APIResourceController")
@RequestMapping(value="api/management/feature")
public class ResourceController {
    @RequestMapping(value = "/list/v1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resourceList(@RequestBody Map<String, Object> map) {
        int page = (int) map.get("page");
        int limit = (int) map.get("limit");
        IResourceService resourceService = new ResourceService();
        PaginationData<Resource> paginationData = resourceService.getResources(page, limit);
        return JsonResponseBuilder.buildSuccessResponse(page,limit,paginationData.getTotal(),paginationData.getPageData());
    }
}
