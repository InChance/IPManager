package controller;

import com.leo.dao.IPDao;
import com.leo.model.IPModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCase {

	@Test
	public void testMapper() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-context.xml");
		IPDao dao = ac.getBean(IPDao.class);
		IPModel u1 = dao.getByIp("127.0.0.1");
		System.out.println(u1.toString());
	}
}
