package com.leo.service.impl;

import com.leo.dao.StudentDao;
import com.leo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentServiceImpl implements IStudentService {

//    @Autowired
//    private StudentDao dao;

    @Override
    public String getStudent(int id){
        return null;//dao.get(id).toString();
    }

}
