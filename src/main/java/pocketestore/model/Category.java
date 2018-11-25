package pocketestore.model;

public class Category {
    private String id;
    private String categoryName;
    private String parentId;

    public Category(){

    }

    public Category(String id, String categoryName) {
        this(id, null, categoryName);
    }

    public Category(String id, String parentId, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
