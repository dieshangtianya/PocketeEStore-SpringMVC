package pocketestore.model;

import java.util.*;

public class CategorySet {
    private String id;
    private String categorySetName;
    private ArrayList<Category> categoryList;

    public CategorySet() {

    }

    public CategorySet(String id) {
        this(id, null);
    }

    public CategorySet(String id, String setName) {
        this.id = id;
        this.categorySetName = setName;
        this.categoryList = new ArrayList<Category>();
    }

    public String getCategorySetName() {
        return categorySetName;
    }

    public void setCategorySetName(String categorySetName) {
        this.categorySetName = categorySetName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }
}
