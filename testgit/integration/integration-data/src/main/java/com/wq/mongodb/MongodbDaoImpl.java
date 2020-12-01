package com.wq.mongodb;

import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongodbDaoImpl implements IMongodbDao {

	/**
	 * MongoDB模板类
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	
	/**
	 * 方法用途: 创建表
	 * 实现步骤: 表不存在时创建<br>
	 * @param collectionName 表名
	 */
	public void createCollection(String collectionName) {
		if(!mongoTemplate.collectionExists(collectionName)) {
			mongoTemplate.createCollection(collectionName);
		}
	}


	public DBCollection getCollection(String collectionName) {
		if(!mongoTemplate.collectionExists(collectionName)) {
			return mongoTemplate.getCollection(collectionName);
		}
		return null;
	}
	/**
	 * 方法用途: 删除表
	 * 实现步骤: 表存在时删除<br>
	 * @param collectionName 表名
	 */
	public void dropCollection(String collectionName) {
		if(mongoTemplate.collectionExists(collectionName)) {
			mongoTemplate.dropCollection(collectionName);
		}
	}

	/**
	 * 方法用途: 获取单独对象
	 * 实现步骤: 这里是通过类来获取对象<br>
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @return 对象
	 */
	@Override
	public <T> T findOne(Class<T> clazz, Query query) {
		return mongoTemplate.findOne(query, clazz);
	}

	/**
	 * 方法用途: 获取单独对象
	 * 实现步骤: 这里是通过类来获取对象<br>
	 * @param collectionName 表名
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @return 对象
	 */
	public <T> T findOne(String collectionName, Class<T> clazz, Query query) {
		return mongoTemplate.findOne(query, clazz, collectionName);
	}

	/**
	 * 方法用途: 保存对象
	 * 实现步骤: 直接保存对象<br>
	 * @param T 对象
	 */
	@Override
	public void save(Object entity) {
		mongoTemplate.save(entity);
	}

	/**
	 * 方法用途: 保存对象
	 * 实现步骤: 直接保存对象<br>
	 * @param collectionName 表名
	 * @param T 对象
	 */
	@Override
	public void save(String collectionName, Object entity) {
		mongoTemplate.save(entity, collectionName);
	}

	/**
	 * 方法用途: 获取所有对象
	 * 实现步骤: 通过类的CALSS对象获取所有数据<br>
	 * @param clazz 类的CALSS对象
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		return mongoTemplate.findAll(clazz);
	}

	/**
	 * 方法用途: 获取所有对象
	 * 实现步骤: 通过类的CALSS对象获取所有数据<br>
	 * @param collectionName 表名
	 * @param clazz 类的CALSS对象
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> findAll(String collectionName, Class<T> clazz) {
		return mongoTemplate.findAll(clazz, collectionName);
	}

	/**
	 * 方法用途: 获取对象
	 * 实现步骤: 根据ID得到对象<br>
	 * @param clazz 类的CALSS对象
	 * @param id ID键值
	 * @return 对象
	 */
	@Override
	public <T> T findById(Class<T> clazz, Object id) {
		return mongoTemplate.findById(id, clazz);
	}

	/**
	 * 方法用途: 获取对象
	 * 实现步骤: 根据ID得到对象<br>
	 * @param collectionName 表名
	 * @param clazz 类的CALSS对象
	 * @param id ID键值
	 * @return 对象
	 */
	@Override
	public <T> T findById(String collectionName, Class<T> clazz, Object id) {
		return mongoTemplate.findById(id, clazz, collectionName);
	}

	/**
	 * 方法用途: 获取对象列表
	 * 实现步骤: 根据查询条件得到对象列表<br>
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> find(Class<T> clazz, Query query) {
		return mongoTemplate.find(query, clazz);
	}

	/**
	 * 方法用途: 获取对象列表
	 * 实现步骤: 根据查询条件得到对象列表<br>
	 * @param collectionName 表名
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> find(String collectionName, Class<T> clazz, Query query) {
		return mongoTemplate.find(query, clazz, collectionName);
	}

	/**
	 * 方法用途: 分页获取对象列表
	 * 实现步骤: 根据查询条件分页得到对象列表<br>
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @param currentPage当前页
	 * @param pagesize 总页数
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> findList(Class<T> clazz, Query query, int currentPage,
										   int pageSize) {
		int startIndex = ((currentPage - 1)<0?0:(currentPage - 1))*pageSize;
		query.skip(startIndex);
		query.limit(pageSize);
		return mongoTemplate.find(query,clazz);
	}

	/**
	 * 方法用途: 分页获取对象列表
	 * 实现步骤: 根据查询条件分页得到对象列表<br>
	 * @param collectionName 表名
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @param currentPage当前页
	 * @param pagesize 总页数
	 * @return 对象列表
	 */
	@Override
	public <T> List<T> findList(String collectionName, Class<T> clazz, Query query, int currentPage,
										   int pageSize) {
		int startIndex = ((currentPage - 1) < 0 ? 0 : (currentPage - 1))*pageSize;
		query.skip(startIndex);
		query.limit(pageSize);
		return mongoTemplate.find(query, clazz, collectionName);
	}

	/**
	 * 方法用途: 得到结果集数
	 * 实现步骤: 根据查询条件分页得到对象集数<br>
	 * @param clazz 类的CALSS对象
	 * @param query 查询条件类
	 * @return 对象数量
	 */
	@Override
	public long findCount(Class<?> clazz, Query query) {
		return mongoTemplate.count(query, clazz);
	}

	/**
	 * 方法用途: 得到结果集数
	 * 实现步骤: 根据查询条件分页得到对象集数<br>
	 * @param collectionName 表名
	 * @param query 查询条件类
	 * @return 对象数量
	 */
	@Override
	public long findCount(String collectionName, Query query) {
		return mongoTemplate.count(query, collectionName);
	}

	/**
	 * 方法用途: 更新数据
	 * 实现步骤: 根据条件更新对象<br>
	 * @param query 查询条件类
	 * @param update 更新数据
	 * @param clazz 对象类
	 *
	 */
	@Override
	public int update(Query query, Update update, Class<?> clazz) {
		return mongoTemplate.updateFirst(query, update, clazz).getN();
	}

	/**
	 * 方法用途: 更新数据
	 * 实现步骤: 根据条件更新对象<br>
	 * @param collectionName 表名
	 * @param query 查询条件类
	 * @param update 更新数据
	 * @param clazz 对象类
	 *
	 */
	@Override
	public int update(String collectionName, Query query, Update update) {
		return mongoTemplate.updateFirst(query, update, collectionName).getN();
	}

	@Override
	public int upsert(String collectionName, Query query, Update update) {
		return mongoTemplate.upsert(query, update, collectionName).getN();
	}

	/**
	 * 方法用途: 删除数据
	 * 实现步骤: 删除对象<br>
	 * @param clazz 对象类
	 * @return 对象列表
	 */
	@Override
	public void remove(Object object) {
		mongoTemplate.remove(object);
	}

	/**
	 * 方法用途: 删除数据
	 * 实现步骤: 删除对象<br>
	 * @param collectionName 表名
	 * @param clazz 对象类
	 * @return 对象列表
	 */
	@Override
	public void remove(String collectionName, Object object) {
		mongoTemplate.remove(object, collectionName);
	}

	@Override
	public void removeQueryData(Query query, String collectionName) {
		mongoTemplate.remove(query, collectionName);
	}

	public static void main(String[] args) {
		//mongodbDao.createCollection("jimt");
		/*long time = System.currentTimeMillis();
		Map<String, Object> obj = new HashMap<String, Object>();
		for (int i=0;i<10000;i++) {

			obj = new HashMap<String, Object>();
			obj.put("name", "jim");
			obj.put("age", i);
			obj.put("weight", "68");
			mongodbDao.save("jimt", obj);


			mongodbDao.find("jimt", HashMap.class, new Query(Criteria.where("age").is(i)));
		}

		System.out.println(System.currentTimeMillis()-time);
		*/
	}


}
