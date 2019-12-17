package com.company.tests;
import com.company.entity.Grade;
import com.company.entity.Student;
import com.company.exceptions.ValidationException;
import com.company.service.GradeService;
import com.company.service.StudentService;
import org.junit.Test;


import java.util.Optional;

import static org.junit.Assert.*;

public class FilterTestst {
    @Test
    public void FilterTest() throws ValidationException {

         StudentService<String, Student<String>> studentService = new StudentService<>("XMLTests.xml");
         Student<String> s1 = new Student<>("1","David","Bowie","111","dbowie");
         Student<String> s2 = new Student<>("2","Roger","Waters","356","roggy");
         Student<String> s3 = new Student<>("3","Nick","Manson","356","nickman");
         Student<String> s4 = new Student<>("4","David","Gilmour","356","theboss");
         Student<String> s5 = new Student<>("5","Richard","Wright","356","rick");
         studentService.add(s1);
         studentService.add(s2);
         studentService.add(s3);
         studentService.add(s4);
         studentService.add(s5);
         assertEquals(studentService.filterGroup("111").count(),1);
         assertEquals(studentService.filterGroup("356").count(),4);
         assertEquals(studentService.filterGroup("111").findFirst(), Optional.of(s1));
         assertEquals(studentService.filterGroup("356").findFirst(),Optional.of(s2));
         assertEquals(studentService.filterGroup("346").count(),0);
         studentService.delete("1");
         studentService.delete("2");
         studentService.delete("3");
         studentService.delete("4");
         studentService.delete("5");
         GradeService<String, Grade<String>> gradeService=new GradeService<>("XMLTests.xml");
         Grade<String> g1 = new Grade<>("1_1","1","1",10,"prof","fed");
         Grade<String> g2 = new Grade<>("1_2","1","2",9,"proffff","fed2");
         Grade<String> g3 = new Grade<>("2_2","2","2",8,"proffffff","fed3");
         gradeService.add(g1);
         gradeService.add(g2);
         gradeService.add(g3);

         assertEquals(gradeService.filterHomework("1").count(),1);
         assertEquals(gradeService.filterHomework("1").findFirst(),Optional.of(g1));
         assertEquals(gradeService.filterHomework("2").count(),2);
         assertEquals(gradeService.filterHomework("2").findFirst(),Optional.of(g2));
         assertEquals(gradeService.filterHomework("3").count(),0);
         assertEquals(gradeService.filterHomeworkTeacher("1","prof").findFirst(),Optional.of(g1));
         assertEquals(gradeService.filterHomeworkTeacher("2","proffff").findFirst(),Optional.of(g2));
         assertEquals(gradeService.filterHomeworkTeacher("3","proffffff").findFirst(),Optional.empty());
         assertEquals(gradeService.filterHomeworkWeek("1",9).findFirst(),Optional.empty());
         assertEquals(gradeService.filterHomeworkWeek("1",9).count(),0);
         assertEquals(gradeService.filterHomeworkWeek("2",9).findFirst(),Optional.empty());
         assertEquals(gradeService.filterHomeworkWeek("2",9).count(),0);
         assertEquals(gradeService.filterHomeworkWeek("3",9).findFirst(),Optional.empty());

         gradeService.delete("1_1");
         gradeService.delete("1_2");
         gradeService.delete("2_2");
    }

}
