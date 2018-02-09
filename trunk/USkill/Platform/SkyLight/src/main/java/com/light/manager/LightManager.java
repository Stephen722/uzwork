package com.light.manager;

import java.util.List;
import java.util.Map;

import com.light.common.Constants;
import com.light.dao.MybatisDao;
import com.light.record.Record;
import com.light.record.RecordBuilder;
import com.light.record.RecordSet;
import com.light.record.ResultList;

/**
 * 基础管理类，包括所有公用的基本操作。
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class LightManager {
	
	private MybatisDao mybatisDao;
	
	/**
	 * 根据statement名称返回一个查询结果。
	 * 
	 * @param id statement名称
	 * @return Object 查询结果对象
	 */
	public <T> T select(String id){
		return getMybatisDao().selectOne(id);
	}

	/**
	 * 根据statement名称和相关参数返回一个查询结果。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return Object 查询结果对象
	 */
	public <T> T select(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().selectOne(id, obj);
	}
	
	/**
	 * 根据statement名称返回所有查询结果。
	 * 
	 * @param id statement名称
	 * @return List 所有查询结果
	 */
	public <E> List<E> selectList(String id){
		return getMybatisDao().selectList(id);
	}
	
	/**
	 * 根据statement名称和相关参数返回所有查询结果。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return List 所有查询结果
	 */
	public <E> List<E> selectList(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().selectList(id, obj);
	}
	
	/**
	 * 查询数据，以ResultList的形式返回查询结果和所有记录总数。
	 * 查询所有记录总数的statmentId = id + "TotalRecord"
	 * 
	 * @param id
	 * @param parameterObject
	 * @return 查询结果和所有记录总数
	 */
	public <E> ResultList<E> selectResultList(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().selectResultList(id, obj);
	}
	
	/**
	 * 根据statement名称和分页查询参数返回所有查询结果。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @param page 当前页
	 * @param pageSize 每页大小
	 * @return List 所有查询结果
	 */
	public <E> List<E> selectPageList(String id, Object obj, int page, int pageSize){
		int begin = (page == 0) ? 0: (page - 1) * pageSize;
		return selectList(id, obj, begin, pageSize);
	}
	
	/**
	 * 根据statement名称和相关参数返回所有查询结果。
	 * 在分页的时候，传递过来的obj必须是Record或者Map，否则skip和max失效，因为系统没有采用Ibatis的RowBounds。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @param skip 查询开始位置
	 * @param max  查询个数
	 * @return List 所有查询结果
	 */
	public <E> List<E> selectList(String id, Object obj, int skip, int max){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().selectList(id, obj, skip, max);
	}

	/**
	 * 根据statement名称和分页查询参数返回所有查询结果。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return Map 所有查询结果
	 */
	public <K, V> Map<K, V> selectPageMap(String id, Object obj, String key){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().selectMap(id, obj, key);
	}
	
	/**
	 * 根据statement名称和分页查询参数返回所有查询结果。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @param page 当前页
	 * @param pageSize 每页大小
	 * @return Map 所有查询结果
	 */
	public <K, V> Map<K, V> selectPageMap(String id, Object obj, String key, int page, int pageSize){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		int begin = (page == 0) ? 0: (page - 1) * pageSize;
		return getMybatisDao().selectMap(id, obj, key, begin, pageSize);
	}
	
	/**
	 * 根据statement名称返回一个Record查询结果。
	 * 
	 * @param id statement名称
	 * @return Record Record查询结果
	 */
	public Record selectRecord(String id){
		Object rtnObj = select(id);
		return RecordBuilder.buildRecord(rtnObj);
	}
	
	/**
	 * 根据statement名称和相关参数返回一个Record查询结果。
	 * 如果obj是一个Record对象，需要传递Record.getRecordMap()。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return Record Record查询结果
	 */
	public Record selectRecord(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		Object rtnObj = select(id, obj);
		return RecordBuilder.buildRecord(rtnObj);
	}
	
	/**
	 * 根据statement名称插入一条记录。
	 * 
	 * @param id statement名称
	 * @return int 成功插入的记录数
	 */
	public int insert(String id){
		return getMybatisDao().insert(id);
	}
	
	/**
	 * 根据statement名称和相关参数插入一条记录。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return int 成功插入的记录数
	 */
	public int insert(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().insert(id, obj);
	}
	
	/**
	 * 根据statement名称和相关参数批量插入记录，支持事务。
	 * @param <T>
	 * 
	 * @param id statement名称
	 * @param objects 参数对象
	 * @return int 批量插入（全部）成功/失败
	 */
	public <T> int insertBatch(String id, List<T> objects){
		return getMybatisDao().insertBatch(id, objects);
	}
	
	/**
	 * 根据statement名称更新一条记录。
	 * 
	 * @param id statement名称
	 * @return int 成功更新的记录数
	 */
	public int update(String id){
		return getMybatisDao().update(id);
	}
	
	/**
	 * 根据statement名称和相关参数更新一条记录。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return int 成功更新的记录数
	 */
	public int update(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().update(id, obj);
	}
	
	/**
	 * 根据statement名称和相关参数批量更新记录，支持事务。
	 * @param <T>
	 * 
	 * @param id statement名称
	 * @param objects 参数对象
	 * @return int 批量更新（全部）成功/失败
	 */
	public <T> int updateBatch(String id, List<T> objects){
		return getMybatisDao().updateBatch(id, objects);
	}
	
	/**
	 * 根据statement名称删除一条记录。
	 * 
	 * @param id statement名称
	 * @return int 成功删除的记录数
	 */
	public int delete(String id){
		return getMybatisDao().delete(id);
	}
	
	/**
	 * 根据statement名称和相关参数删除一条记录。
	 * 
	 * @param id statement名称
	 * @param obj 参数对象
	 * @return int 成功删除的记录数
	 */
	public int delete(String id, Object obj){
		if(obj instanceof Record){
			obj = ((Record)obj).getRecordMap();
		}
		return getMybatisDao().delete(id, obj);
	}

	/**
	 * 根据statement名称和相关参数批量删除记录，支持事务。
	 * 
	 * @param id statement名称
	 * @param objects
	 * @return int 批量删除（全部）成功/失败
	 */
	public <T> int deleteBatch(String id, List<T> objects){
		return getMybatisDao().deleteBatch(id, objects);
	}
	
	/**
	 * 根据statement名称和返回一个RecordSet查询结果。
	 * 
	 * @param id statement名称
	 * @return RecordSet RecordSet查询结果
	 */
	public RecordSet selectRecordSet(String id){
		List<Object> rtnList = selectList(id);
		return RecordBuilder.buildRecordSet(rtnList);
	}
	
	/**
	 * 根据statement名称和相关参数返回一个RecordSet查询结果，并且设置inputRecord为其SummaryRecord。
	 * 
	 * @param id statement名称
	 * @param inputRecord 参数对象
	 * @return RecordSet RecordSet查询结果
	 */
	public RecordSet selectRecordSet(String id, Record inputRecord){
		int skip = inputRecord.getIntegerValue(Constants.PAGE, 0);
		int max = inputRecord.getIntegerValue(Constants.PAGE_SIZE, 0);
		List<Object> rtnList = selectList(id, inputRecord, skip, max);
		RecordSet recordSet = RecordBuilder.buildRecordSet(rtnList);
		recordSet.getSummaryRecord().setRecord(inputRecord);
		return recordSet;
	}
	
	/**
	 * 根据statement名称和相关参数返回一个RecordSet查询结果，并且设置inputRecord为其SummaryRecord。
	 * 
	 * @param id statement名称
	 * @param inputRecord 参数对象
	 * @param skip 查询开始位置
	 * @param max  查询个数
	 * @return RecordSet RecordSet查询结果
	 */
	public RecordSet selectRecordSet(String id, Record inputRecord, int skip, int max){
		List<Object> rtnList = selectList(id, inputRecord.getRecordMap(), skip, max);
		RecordSet recordSet = RecordBuilder.buildRecordSet(rtnList);
		recordSet.getSummaryRecord().setRecord(inputRecord);
		return recordSet;
	}

	public MybatisDao getMybatisDao() {
		return mybatisDao;
	}

	public void setMybatisDao(MybatisDao mybatisDao) {
		this.mybatisDao = mybatisDao;
	}

}
