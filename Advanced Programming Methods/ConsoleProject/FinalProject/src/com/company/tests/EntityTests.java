package com.company.tests;

import com.company.entity.Grade;
import com.company.entity.Homework;
import com.company.entity.Student;
import org.junit.Test;


import static org.junit.Assert.*;


public class EntityTests {

    @Test
    public void StudentTest(){
        Student<String> stud1 = new Student<>("","","","","");
        assertEquals(stud1.getId(),"");
        assertEquals(stud1.getFirstName(),"");
        assertEquals(stud1.getLastName(),"");
        assertEquals(stud1.getGroup(),"");
        assertEquals(stud1.getEmail(),"");
        stud1.setId("1");
        stud1.setFirstName("John");
        stud1.setLastName("Copper");
        stud1.setGroup("316");
        stud1.setEmail("jonny23@hotmail.com");
        assertEquals(stud1.getId(),"1");
        assertEquals(stud1.getFirstName(),"John");
        assertEquals(stud1.getLastName(),"Copper");
        assertEquals(stud1.getGroup(),"316");
        assertEquals(stud1.getEmail(),"jonny23@hotmail.com");
    }

    @Test
    public void HomeworkTest(){
        Homework<String> homework1 = new Homework<>("",0,"");
        assertEquals(homework1.getId(),"");
        assertEquals(homework1.getDeadlineWeek(),0);
        assertEquals(homework1.getDescription(),"");
        Homework<String> homework2 = new Homework<>("",0,0,"");
        assertEquals(homework2.getId(),"");
        assertEquals(homework2.getStartWeek(),0);
        assertEquals(homework2.getDeadlineWeek(),0);
        assertEquals(homework2.getDescription(),"");
        homework2.setId("1");
        homework2.setStartWeek(9);
        homework2.setDeadlineWeek(14);
        homework2.setDescription("desc");
        assertEquals(homework2.getId(),"1");
        assertEquals(homework2.getStartWeek(),9);
        assertEquals(homework2.getDeadlineWeek(),14);
        assertEquals(homework2.getDescription(),"desc");
    }

    @Test
    public void GradeTest(){
        Grade<String> grade = new Grade<>("","","",0,"","");
        grade.setId("1_1");
        grade.setStudentId("1");
        grade.setHomeworkID("1");
        grade.setGrade(10);
        grade.setProf("prof");
        grade.setFeedback("fed");
        assertEquals(grade.getId(),"1_1");
        assertEquals(grade.getStudentId(),"1");
        assertEquals(grade.getHomeworkID(),"1");
        assertEquals(grade.getGrade(),10);
        assertEquals(grade.getProf(),"prof");
        assertEquals(grade.getFeedback(),"fed");
    }
}
