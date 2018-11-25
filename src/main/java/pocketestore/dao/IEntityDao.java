package pocketestore.dao;

import pocketestore.model.PaginationData;

import java.util.*;

public interface IEntityDao<T> {
    /**
     * get all entities from db
     *
     * @return List<T>
     */
    List<T> getAll() throws Exception;

    /**
     * get data by page index and page size
     *
     * @param pageIndex
     * @param pageSize
     * @return List<T>
     */
     PaginationData<T> get(int pageIndex, int pageSize) throws Exception;

    /**
     * get entity by id
     *
     * @param id
     * @return T
     */
    T getById(String id) throws Exception;

    /**
     * add new entity
     *
     * @param entity
     * @return boolean represent success or fail
     */
    boolean add(T entity) throws Exception;

    /**
     * update a entity
     *
     * @param entity
     * @return boolean represent success or fail
     */
    boolean update(T entity) throws Exception;

    /**
     * remove a entity by id
     *
     * @param id
     * @return boolean represent success or fail
     */
    boolean remove(String id) throws Exception;
}
