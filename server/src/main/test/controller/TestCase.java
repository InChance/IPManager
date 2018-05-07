package controller;

import com.leo.dao.IPMaskDao;
import com.leo.model.IPMaskModel;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCase {

	@Test
	public void testMapper() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		IPMaskDao dao = ac.getBean(IPMaskDao.class);
		IPMaskModel u1 = dao.get(1);
		System.out.println(u1.toString());
	}
}
