package com.light.dao;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.exception.MybatisException;
import com.light.record.ResultList;

/**
 * MybatisDao 基于Mybatis3提供的数据库的相关操作的。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class MybatisDao {

	private static final Logger logger = LogManager.getLogger(MybatisDao.class);
	
	public static final String BEGIN = "begin";
	public static final String END = "end";
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String TOTAL_RECORD = "TotalRecord";
	
	private String configuration;
	
	private static SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 插入一条记录。
	 * 
	 * @param id
	 * @return 新插入的主键
	 */
	public int insert(String id) {
		int rtnInt = 0;
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			rtnInt = sqlSession.insert(id);
		}
		catch(Exception e){
			logger.error("Failed to insert for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to insert for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnInt;
	}

	/**
	 * 插入一条记录。
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 新插入的主键
	 */
	public int insert(String id, Object parameterObject) {
		int rtnInt = 0;
		if (parameterObject == null) {
			rtnInt = this.insert(id);
		}
		else {
			SqlSession sqlSession = getSqlSessionFactory().openSession(true);
			try {
				rtnInt = sqlSession.insert(id, parameterObject);
			}
			catch(Exception e){
				logger.error("Failed to insert for '" + id + "'." + "\n Exception:" + e.toString());
				MybatisException me = new MybatisException("Failed to insert for '" + id + "'", e);
	            throw me;
			}
			finally {
				sqlSession.close();
			}
		}
		return rtnInt;
	}
	
	/**
	 * 批量插入记录，支持事务。
	 * 
	 * @param id
	 * @param objects
	 * @return int 批量插入（全部）成功/失败
	 */
	public <T> int insertBatch(String id, List<T> objects) {
		int rtn = 1;
		int unitCount = 300;
		if (objects != null) {
			SqlSession sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
				int objSize = objects.size(); 
				for(int i = 0; i < objSize; i++){
					Object obj = objects.get(i);
					sqlSession.insert(id, obj);
					
					if(i != 0 && i % unitCount == 0){
						sqlSession.commit();
						sqlSession.clearCache();
					}
				}
				sqlSession.commit();
			}
			catch(Exception e){
				rtn = 0;
				sqlSession.rollback(); // Only roll back the data which have not been committed.
				logger.error("Failed to insert batch for '" + id + "'." + "\n Exception:" + e.toString());
			}
			finally {
				sqlSession.close();
			}
		}
		return rtn;
	}

	/**
	 * 更新记录。
	 * 
	 * @param id
	 * @return 更新的记录数
	 */
	public int update(String id) {
		int rtnInt = 0;
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			rtnInt = sqlSession.update(id);
		}
		catch(Exception e){
			logger.error("Failed to update for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to update for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnInt;
	}

	/**
	 * 更新数据库。
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 更新的记录数
	 */
	public int update(String id, Object parameterObject) {
		int rtnInt = 0;
		if (parameterObject == null) {
			rtnInt = this.update(id);
		}
		else {
			SqlSession sqlSession = getSqlSessionFactory().openSession(true);
			try {
				rtnInt = sqlSession.update(id, parameterObject);
			}
			catch(Exception e){
				logger.error("Failed to update for '" + id + "'." + "\n Exception:" + e.toString());
				MybatisException me = new MybatisException("Failed to update for '" + id + "'", e);
	            throw me;
			}
			finally {
				sqlSession.close();
			}
		}
		return rtnInt;
	}

	/**
	 * 批量更新记录，支持事务。
	 * @param <T>
	 * 
	 * @param id
	 * @param objects
	 * @return int 批量更新（全部）成功/失败
	 */
	public <T> int updateBatch(String id, List<T> objects) {
		int rtn = 1;
		int unitCount = 200;
		if (objects != null) {
			SqlSession sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
				int objSize = objects.size(); 
				for(int i = 0; i < objSize; i++){
					Object obj = objects.get(i);
					sqlSession.update(id, obj);
					if(i != 0 && i % unitCount == 0){
						sqlSession.commit();
						sqlSession.clearCache();
					}
				}
				sqlSession.commit();
			}
			catch(Exception e){
				rtn = 0;
				sqlSession.rollback(); // Only roll back the data which have not been committed.
				logger.error("Failed to update batch for '" + id + "'." + "\n Exception:" + e.toString());
			}
			finally {
				sqlSession.close();
			}
		}
		return rtn;
	}
	
	/**
	 * 删除记录。
	 * 
	 * @param id
	 * @return 删除的记录数
	 */
	public int delete(String id) {
		int rtnInt = 0;
		SqlSession sqlSession = getSqlSessionFactory().openSession(true);
		try {
			rtnInt = sqlSession.delete(id);
		}
		catch(Exception e){
			logger.error("Failed to delete for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to delete for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnInt;
	}

	/**
	 * 删除记录。
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 删除的记录数
	 */
	public int delete(String id, Object parameterObject) {
		int rtnInt = 0;
		if (parameterObject == null) {
			rtnInt = this.delete(id);
		}
		else {
			SqlSession sqlSession = getSqlSessionFactory().openSession(true);
			try {
				rtnInt = sqlSession.delete(id, parameterObject);
			}
			catch(Exception e){
				logger.error("Failed to delete for '" + id + "'." + "\n Exception:" + e.toString());
				MybatisException me = new MybatisException("Failed to delete for '" + id + "'", e);
	            throw me;
			}
			finally {
				sqlSession.close();
			}
		}
		return rtnInt;
	}

	/**
	 * 批量删除记录，支持事务。
	 * 
	 * @param id
	 * @param objects
	 * @return int 批量删除（全部）成功/失败
	 */
	public <T> int deleteBatch(String id, List<T> objects) {
		int rtn = 1;
		int unitCount = 200;
		if (objects != null) {
			SqlSession sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
				int size = objects.size(); 
				for(int i = 0; i < size; i++){
					Object obj = objects.get(i);
					sqlSession.delete(id, obj);
					if(i != 0 && i % unitCount == 0){
						sqlSession.commit();
						sqlSession.clearCache();
					}
				}
				sqlSession.commit();
			}
			catch(Exception e){
				rtn = 0;
				sqlSession.rollback(); // Only roll back the data which have not been committed.
				logger.error("Failed to delete batch for '" + id + "'." + "\n Exception:" + e.toString());
			}
			finally {
				sqlSession.close();
			}
		}
		return rtn;
	}
	
	/**
	 * 查询数据。
	 * 
	 * @param id
	 * @return 查询结果
	 */
	public <T> T selectOne(String id) {
		T t = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			t = sqlSession.selectOne(id);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return t;
	}

	/**
	 * 查询数据。
	 * 
	 * @param id
	 * @param obj
	 * @return 查询结果 
	 */
	public <T> T selectOne(String id, Object obj) {
		T rtnObj = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			rtnObj = sqlSession.selectOne(id, obj);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnObj;
	}

	/**
	 * 查询数据，以List的形式返回查询结果。
	 * 
	 * @param id
	 * @return 查询结果
	 */
	public <E> List<E> selectList(String id) {
		List<E> rtnList = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			rtnList = sqlSession.selectList(id);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnList;
	}

	/**
	 * 查询数据，以List的形式返回查询结果。
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 查询结果
	 */
	public <E> List<E> selectList(String id, Object parameterObject) {
		List<E> rtnList = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			rtnList = sqlSession.selectList(id, parameterObject);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnList;
	}

	/**
	 * 查询数据，以List的形式返回查询结果。
	 * 在大量数据的时候，ibatis的RowBunds方法效率不高。
	 * 
	 * @param id
	 * @param parameterObject
	 * @param skip
	 * @param max
	 * @return 查询结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <E> List<E> selectList(String id, Object parameterObject, int skip, int max) {
		List<E> rtnList = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		//RowBounds rowBounds = new RowBounds(skip, max);
		if(parameterObject instanceof Map) {
			((Map)parameterObject).put(BEGIN, skip);
			((Map)parameterObject).put(END, max);
		}
		try {
			//rtnList = sqlSession.selectList(id, parameterObject, rowBounds);
			rtnList = sqlSession.selectList(id, parameterObject);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnList;
	}

	/**
	 * 查询数据，以ResultList的形式返回查询结果和所有记录总数。
	 * 查询所有记录总数的statmentId = id + "TotalRecord"
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 查询结果和所有记录总数
	 */
	public <E> ResultList<E> selectResultList(String id, Object parameterObject) {
		ResultList<E> rtnList = new ResultList<E>();
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			List<E> rsList = sqlSession.selectList(id, parameterObject);
			int totalRecord = sqlSession.selectOne(id + TOTAL_RECORD, parameterObject);
			rtnList.setTotal(totalRecord);
			rtnList.setResults(rsList);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnList;
	}
	
	/**
	 * 查询数据，以Map的形式返回查询结果。
	 * 
	 * @param id
	 * @param parameterObject
	 * @param skip
	 * @param max
	 * @param key
	 * @return 查询结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K, V> Map<K, V> selectMap(String id, Object parameterObject, String key, int skip, int max) {
		Map<K, V> rtnMap = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		if(parameterObject instanceof Map) {
			((Map)parameterObject).put(BEGIN, skip);
			((Map)parameterObject).put(END, max);
		}
		try {
			rtnMap = sqlSession.selectMap(id, parameterObject, key);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnMap;
	}

	/**
	 * 查询数据，以Map的形式返回查询结果。
	 * 
	 * @param id
	 * @param parameterObject
	 * @param key
	 * @return 查询结果
	 */
	public <K, V> Map<K, V> selectMap(String id, Object parameterObject, String key) {
		Map<K, V> rtnMap = null;
		SqlSession sqlSession = getSqlSessionFactory().openSession();
		try {
			rtnMap = sqlSession.selectMap(id, parameterObject, key);
		}
		catch(Exception e){
			logger.error("Failed to select for '" + id + "'." + "\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to select for '" + id + "'", e);
            throw me;
		}
		finally {
			sqlSession.close();
		}
		return rtnMap;
	}
	
	/**
	 * 获取SqlSessionFactory。
	 * 
	 * @return SqlSessionFactory
	 */
	public SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = initializeSqlSessionFactory();
		}
		
		return sqlSessionFactory;
	}
	
	/**
	 * 初始化SqlSessionFactory。
	 * 
	 * @return SqlSessionFactory
	 */
	private SqlSessionFactory initializeSqlSessionFactory() {
		logger.info("Starting to initialize SqlSessionFactory.");
		SqlSessionFactory sessionFactory = null;
		try {
			String resource = configuration;
			// 读取配置文件时候指定编码，避免配置文件中有中文的问题。
			Resources.setCharset(Charset.forName("UTF-8"));
			Reader reader = Resources.getResourceAsReader(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			if (sessionFactory == null) {
				logger.info("Initialized SqlSessionFactory unsuccessfully.");
			}
			else {
				logger.info("Initialized SqlSessionFactory successfully.");
			}
		}
		catch (Exception e) {
			logger.error("Failed to initialize SqlSessionFactory.\n Exception:" + e.toString());
			MybatisException me = new MybatisException("Failed to initialize SqlSessionFactory.", e);
			throw me;
		}
		return sessionFactory;
	}
    
	public String getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
}
