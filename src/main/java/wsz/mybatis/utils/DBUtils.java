package wsz.mybatis.utils;
/**
 *
 *@author  wsz
 *@createdTime 2018年3月26日
*/

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtils {
	
	private static SqlSessionFactory sessionFactory;
	
	static {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream("mybatis-config.xml");
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sessionFactory;
	}
	
	public static SqlSession getSqlSession() {
		return sessionFactory.openSession();
	}
	
	public static void main(String[] args) {
		System.out.println(DBUtils.sessionFactory.openSession());
	}
}
