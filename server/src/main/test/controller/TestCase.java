package controller;

import com.leo.dao.StudentDao;
import com.leo.model.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCase {

	@Test
	public void testMapper() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		StudentDao dao = ac.getBean(StudentDao.class);
		Student u1 = dao.get(1);
		System.out.println(u1.toString());
	}
}
