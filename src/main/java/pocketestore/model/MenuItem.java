package pocketestore.model;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String menuId;
    private String menuText;
    private String menuUrl;
    private List<MenuItem> items;

    public MenuItem() {
        this.items = new ArrayList<MenuItem>();
    }

    public MenuItem(String menuId, String menuText) {
        this();
        this.menuId = menuId;
        this.menuText = menuText;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
