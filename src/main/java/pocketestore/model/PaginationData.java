package pocketestore.model;

import java.util.*;

public class PaginationData<T> {

    public PaginationData() {
        this(0, new ArrayList<T>());
    }

    public PaginationData(int total, List<T> pageData) {
        this.total = total;
        this.pageData = pageData;
    }

    private long total;
    private List<T> pageData;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
