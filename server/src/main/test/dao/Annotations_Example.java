package dao;

import com.leo.dao.IPMaskDao;
import com.leo.model.IPMaskModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class Annotations_Example {
    public static void main(String args[]) throws IOException {
        Reader reader = Resources.getResourceAsReader("spring-mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(IPMaskDao.class);

        IPMaskDao dao = session.getMapper(IPMaskDao.class);
        IPMaskModel student = dao.getByIp("127.0.0.1");
        System.out.println(student.toString());

        session.commit();
        session.close();
    }
}
