package wsz.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import wsz.mybatis.entity.User;

/**
 *
 *@author  wsz
 *@createdTime 2018年3月26日
*/
public interface UserMapper {
	/**
	 * 单个插入
	 * @param user
	 * @return
	 */
	int addUser(User user);
	
	/**
	 * 批量插入
	 * @param users
	 * @return
	 */
	int batchAdd(List<User> users);
	
	/**
	 * 单个删除
	 * @param user
	 */
	int deleteUser(User user);
	
	/**
	 * 批量删除
	 * @param users
	 * @return
	 */
	int batchDelete(List<User> users);
	
	/**
	 * 单个更新
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	
	/**
	 * 获取所有用户
	 * @return
	 */
	List<User> selectAll();
	
	/**
	 * 根据id查找User
	 * @param id
	 * @return
	 */
	User findById(int id);
	
	/**
	 * 根据用户名(模糊)查询
	 * @param username
	 * @return
	 */
	List<User> findByUsername(String username);
	
	/**
	 * 根据2个属性查找对象
	 * @param username
	 * @param password
	 * @return
	 */
	List<User> findByUsernameAndPassword(@Param("username")String username,@Param("password")String password) ;
	
	/**
	 * 根据对象中的2个属性查找对象
	 * @param user
	 * @return
	 */
	User findByUser(User user);
	
	/**
	 * ${**}模拟sql注入
	 * @param username
	 * @return
	 */
	List<User> sqlInjec1(@Param("username") String username);
	
	
	/**
	 * ${**}模拟sql注入
	 * @param username
	 * @return
	 */
	List<User> sqlInjec2(@Param("username") String username);
	
	/**
	 * 分页：使用自带的RowBounds
	 * @param rowBounds
	 * @return
	 */
	List<User> page(RowBounds rowBounds);
	
	/**
	 * 调用存储过程:输入+输出
	 * @param map
	 */
	void procedure(Map<String, Object> map); 
	
	/**
	 * 存储过程：比id大的数据列表
	 * @param map
	 * @return
	 */
	List<User> orderId(Map<String, Object> map);
}
