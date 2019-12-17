package com.company.tests;
import com.company.entity.Grade;
import com.company.entity.Homework;
import com.company.entity.Student;
import com.company.exceptions.ValidationException;
import com.company.service.GradeService;
import com.company.service.HomeworkService;
import com.company.service.StudentService;
import org.junit.Test;


import static org.junit.Assert.*;

public class ServiceTests {
    @Test
    public void StudentServiceTest() throws ValidationException {
        StudentService<String, Student<String>> studentService=new StudentService<>("XMLTests.xml");
        Student<String> stud1 = new Student<>("1","A","B","123","AB");
        Student<String> stud2 = new Student<>("1","C","D","456","CD");
        try{
            assertEquals(studentService.find("1"),null);
        }catch (IllegalArgumentException e){}
        studentService.add(stud1);
        assertEquals(studentService.find("1"),stud1);
        studentService.update(stud2);
        studentService.motivateWeek(8,"1");
        assertEquals(studentService.find("1"),stud2);
        studentService.delete("1");
        assertEquals(studentService.getAll().iterator().hasNext(),false);
    }

    @Test
    public void HomeWorkService() throws ValidationException {
        HomeworkService<String, Homework<String>> homeworkService=new HomeworkService<>("XMLTests.xml");
        Homework<String> homework1 = new Homework<>("1",14,"desc");
        Homework<String> homework2 = new Homework<>("2",13,"desc2");
        assertEquals(homeworkService.getAll().iterator().hasNext(),false);
        homeworkService.add(homework1);
        assertEquals(homeworkService.find("1"),homework1);
        assertEquals(homeworkService.find("2"),null);
        homeworkService.delete("1");
        assertEquals(homeworkService.find("1"),null);
        homeworkService.add(homework2);
        homeworkService.update(new Homework<>("2",13,"ddddd"));
        assertEquals(homeworkService.find("1"),null);
        homeworkService.delete("2");
    }

}
