package wsz.mybatis.test;
/**
 *
 *@author  wsz
 *@createdTime 2018年3月26日
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import wsz.mybatis.dao.UserMapper;
import wsz.mybatis.entity.User;
import wsz.mybatis.utils.DBUtils;

public class MybatisTest {
	
	private SqlSession sqlSession;
	
	private UserMapper userMapper;
	
	@Before
	public void init() {
		sqlSession = DBUtils.getSqlSession();
		userMapper = sqlSession.getMapper(UserMapper.class);
	}
	
	@Test
	public void addUser() {
		User user = new User("add", "add");
		int flag = userMapper.addUser(user);
		sqlSession.commit();
		System.out.println(flag+" "+user.getId());
	}
	
	@Test
	public void batchAdd() {
		List<User> list = new ArrayList<User>();
		for(int i =0;i<20;i++) {
			String flag = String.valueOf(i);
			list.add(new User(flag, flag));
		}
		int flag = userMapper.batchAdd(list);
		sqlSession.commit();
		System.out.println(flag);
	}
	
	@Test
	public void deleteUser() {
		User user = userMapper.findById(50);
		int flag = userMapper.deleteUser(user);
		sqlSession.commit();
		System.out.println(flag);
	}
	
	@Test 
	public void batchDelete() {
		List<User> list = new ArrayList<User>();
		for(int i =30;i<40;i++) {
			list.add(userMapper.findById(i));
		}
		int flag = userMapper.batchDelete(list);
		sqlSession.commit();
		System.out.println(flag);
	}
	
	@Test
	public void updateUser() {
		User user = userMapper.findById(51);
		user.setPassword("51");
		int flag = userMapper.updateUser(user);
		sqlSession.commit();
		System.out.println(flag);
	}
	
	@Test
	public void selectAll() {
		List<User> list = userMapper.selectAll();
		System.out.println(list);
	}
	
	@Test
	public void findById() {
		User user = userMapper.findById(51);
		System.out.println(user);
	}
	
	@Test 
	public void findByUsername() {
		List<User> list = userMapper.findByUsername("1");
		System.out.println(list);
	}
	
	@Test 
	public void findByUsernameAndPassword() {
		List<User> list = userMapper.findByUsernameAndPassword("8", "8");
		System.out.println(list);
	}
	
	@Test
	public void findByUser() {
		User user = userMapper.findByUser(new User("8", "8"));
		System.out.println(user);
	}
	
	/**
	 * sql注入查询出所有数据
	 * '%%' 单独也可以查询出全部数据
	 */
	@Test
	public void sqlInjec1() {
		List<User> list = userMapper.sqlInjec1("'%%' or 1=1");
		System.out.println(list);
	}
	
	/**
	 * sql注入查询出所有数据concat连接字符串
	 * sql语句暂未成功
	 */
	@Test
	public void sqlInjec2() {
		List<User> list = userMapper.sqlInjec2("%%");
		System.out.println(list);
	}
	
	@Test
	public void page() {
		//每页5条数据获取第二页数据
		RowBounds rowBounds = new RowBounds(2, 5);
		List<User> list = userMapper.page(rowBounds);
		System.out.println(list);
	}
	
	/**
	 * create procedure countByName(in username VARCHAR(255),out total int(11))
	 * begin
	 * 	select count(*) into total
	 * 	from t_user t
	 * 	where t.username like concat('%',username,'%');
	 * end
	 */
	@Test
	public void procedure() {
		Map<String, Object> map = new HashMap<String, Object>();
		//对应xml中的parameter
		map.put("username", "1");
		map.put("count", null);
		userMapper.procedure(map);
		System.out.println(map.get("count"));
	}
	
	/**
	 * id倒序查找大于id值的数据列表,查询不能直接用id,需要为t.id
	 * create procedure orderId(in id int(11))
	 * begin
	 * 	select t.id,username,password
	 * 	from t_user t
	 * 	where t.id > id
	 * 	order by t.id desc;
	 * end
	 */
	@Test
	public void orderId() {
		Map<String, Object> map = new HashMap<String, Object>();
		//对应xml中的parameter
		map.put("id", 12);
		List<User> list = userMapper.orderId(map);
		System.out.println(list);
	}
	
	/**
	 * 事务模式：
	 * 1.session关闭自动提交
	 * 2.异常
	 * 2.session异常期间的操作不进行提交
	 */
	@Test
	public void transaction() {
		SqlSession session = DBUtils.getSqlSessionFactory().openSession(false);
		UserMapper mapper = session.getMapper(UserMapper.class);
		int flag1 = 0;
		int flag2 = 0;
		try {
			flag1 = mapper.deleteUser(userMapper.findById(53));
			System.out.println(1/0);
			flag2 = mapper.deleteUser(userMapper.findById(54));
			session.commit();
			System.out.println(flag1 +" "+flag2);
		} catch (Exception e) {
			System.out.println(flag1 +" "+flag2);
			e.printStackTrace();
		}
	}
	
	/**
	 * mybatis注解sql
	 */
	@Test
	public void anno() {
		List<User> list = userMapper.anno("1");
		System.out.println(list);
	}
}
