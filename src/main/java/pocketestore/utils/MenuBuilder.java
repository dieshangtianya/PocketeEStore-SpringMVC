package pocketestore.utils;

import pocketestore.model.MenuItem;
import pocketestore.model.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class MenuBuilder {
    public static List<MenuItem> buildMenuFromResourceList(List<Resource> resourceList) {
        List<MenuItem> rootMenuItems = resourceList.stream()
                .filter(resourceItem -> resourceItem.getParentResourceId() == null)
                .map(resourceItem -> generateMenuRecursively(resourceItem, resourceList))
                .collect(Collectors.toList());

        MenuItem homeMenuItem=new MenuItem("000000","系统首页");
        homeMenuItem.setMenuUrl("/management/dashboard");
        rootMenuItems.add(0,homeMenuItem);

        return rootMenuItems;
    }

    private static MenuItem generateMenuRecursively(Resource resourceItem, List<Resource> resourceList) {
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuId(resourceItem.getId());
        menuItem.setMenuText(resourceItem.getResourceName());
        menuItem.setMenuUrl(resourceItem.getPath());
        List<MenuItem> childrenItems = resourceList.stream()
                .filter(item -> resourceItem.getId().equals(item.getParentResourceId()))
                .map(childResource -> generateMenuRecursively(childResource, resourceList))
                .collect(Collectors.toList());
        if (childrenItems.size() > 0) {
            menuItem.getItems().addAll(childrenItems);
        }
        return menuItem;
    }
}
