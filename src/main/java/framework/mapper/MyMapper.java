package framework.mapper;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @ClassName: MyMapper
 * @Description: 通用mapper
 * @author: jinzhaopo
 * @version: V1.0.0-SNAPSHOT
 * @param <T>
 * @date: 2017年5月14日 上午10:49:45
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
	/**
	 * 获取表中的下一个id<br/>
	 * 由于mycat和mapper整合的时候ID主键策略有问题<br/>
	 * 所以先用这个来代替
	 * 
	 * @return
	 */
	public Long selectNextId();

	/**
	 * 
	 * @Title: getListByXml
	 * @Description: 查询(这个查询是可以跨表查询的)
	 * @param filterParams
	 * @return
	 * @return: List
	 */
	public List getListByXml(Map<String, Object> filterParams);
}
