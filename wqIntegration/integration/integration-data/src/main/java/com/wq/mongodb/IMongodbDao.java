package com.wq.mongodb;


import com.mongodb.DBCollection;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


/**
 * 
 * <p>Description:mongodb数据库DAO映射类 </p>
 * @date 2014-8-19
 * @author jim
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public interface IMongodbDao {

    /**
     * 方法用途: 创建表
     * 实现步骤: 表不存在时创建<br>
     * @param collectionName 表名
     */
    void createCollection(String collectionName);

    DBCollection getCollection(String collectionName);

    /**
     * 方法用途: 删除表
     * 实现步骤: 表存在时删除<br>
     * @param collectionName 表名
     */
    void dropCollection(String collectionName);

    /**
     * 方法用途: 获取单独对象
     * 实现步骤: 这里是通过类来获取对象<br>
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @return 对象
     */
    <T> T findOne(Class<T> clazz, Query query);

    /**
     * 方法用途: 获取单独对象
     * 实现步骤: 这里是通过类来获取对象<br>
     * @param collectionName 表名
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @return 对象
     */
    <T> T findOne(String collectionName, Class<T> clazz, Query query);

    /**
     * 方法用途: 保存对象
     * 实现步骤: 直接保存对象<br>
     * @param T 对象
     */
    void save(Object entity);

    /**
     * 方法用途: 保存对象
     * 实现步骤: 直接保存对象<br>
     * @param collectionName 表名
     * @param T 对象
     */
    void save(String collectionName, Object entity);

    /**
     * 方法用途: 获取所有对象
     * 实现步骤: 通过类的CALSS对象获取所有数据<br>
     * @param clazz 类的CALSS对象
     * @return 对象列表
     */
    <T> List<T> findAll(Class<T> clazz);

    /**
     * 方法用途: 获取所有对象
     * 实现步骤: 通过类的CALSS对象获取所有数据<br>
     * @param collectionName 表名
     * @param clazz 类的CALSS对象
     * @return 对象列表
     */
    <T> List<T> findAll(String collectionName, Class<T> clazz);

    /**
     * 方法用途: 获取对象
     * 实现步骤: 根据ID得到对象<br>
     * @param clazz 类的CALSS对象
     * @param id ID键值
     * @return 对象
     */
    <T> T findById(Class<T> clazz, Object id);

    /**
     * 方法用途: 获取对象
     * 实现步骤: 根据ID得到对象<br>
     * @param collectionName 表名
     * @param clazz 类的CALSS对象
     * @param id ID键值
     * @return 对象
     */
    <T> T findById(String collectionName, Class<T> clazz, Object id);

    /**
     * 方法用途: 获取对象列表
     * 实现步骤: 根据查询条件得到对象列表<br>
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @return 对象列表
     */
    <T> List<T> find(Class<T> clazz, Query query);

    /**
     * 方法用途: 获取对象列表
     * 实现步骤: 根据查询条件得到对象列表<br>
     * @param collectionName 表名
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @return 对象列表
     */
    <T> List<T> find(String collectionName, Class<T> clazz, Query query);

    /**
     * 方法用途: 分页获取对象列表
     * 实现步骤: 根据查询条件分页得到对象列表<br>
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @param currentPage当前页
     * @param pagesize 总页数
     * @return 对象列表
     */
    <T> List<T> findList(Class<T> clazz, Query query, int currentPage,
                         int pageSize);

    /**
     * 方法用途: 分页获取对象列表
     * 实现步骤: 根据查询条件分页得到对象列表<br>
     * @param collectionName 表名
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @param currentPage 当前页
     * @param pagesize 总页数
     * @return 对象列表
     */
    <T> List<T> findList(String collectionName, Class<T> clazz, Query query, int currentPage,
                         int pageSize);

    /**
     * 方法用途: 得到结果集数
     * 实现步骤: 根据查询条件分页得到对象集数<br>
     * @param clazz 类的CALSS对象
     * @param query 查询条件类
     * @return 对象数量
     */
    long findCount(Class<?> clazz, Query query);

    /**
     * 方法用途: 得到结果集数
     * 实现步骤: 根据查询条件分页得到对象集数<br>
     * @param collectionName 表名
     * @param query 查询条件类
     * @return 对象数量
     */
    long findCount(String collectionName, Query query);

    /**
     * 方法用途: 更新数据
     * 实现步骤: 根据条件更新对象<br>
     * @param query 查询条件类
     * @param update 更新数据
     * @param clazz 对象类
     *
     */
    int update(Query query, Update update, Class<?> clazz);

    /**
     * 方法用途: 更新数据
     * 实现步骤: 根据条件更新对象<br>
     * @param collectionName 表名
     * @param query 查询条件类
     * @param update 更新数据
     * @param clazz 对象类
     *
     */
    int update(String collectionName, Query query, Update update);

    int upsert(String collectionName, Query query, Update update);

    /**
     * 方法用途: 删除数据
     * 实现步骤: 删除对象<br>
     * @param clazz 对象类
     * @return 对象列表
     */
    void remove(Object object);

    /**
     * 方法用途: 删除数据
     * 实现步骤: 删除对象<br>
     * @param collectionName 表名
     * @param clazz 对象类
     * @return 对象列表
     */
    void remove(String collectionName, Object object);

    /**
     *
     * 方法用途: 删除特性条件的数据<br>
     * 实现步骤: <br>
     * @param query
     * @param collectionName
     */
    void removeQueryData(Query query, String collectionName);



}