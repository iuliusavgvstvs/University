package com.company.tests;

import com.company.entity.Student;
import com.company.repository.GeneralRepository;
import com.company.repository.StudentXMLRepo;
import org.junit.Test;


import static org.junit.Assert.*;

public class RepoTests {

    @Test
    public void GeneralRepoTest(){
        GeneralRepository<String, Student<String>> repo = new GeneralRepository<>();
        Student<String> stud1 = new Student<>("1","A","B","123","AB");
        Student<String> stud2 = new Student<>("1","C","D","456","CD");
        assertEquals(repo.findOne("1"),null);
        repo.save(stud1);
        assertEquals(repo.findOne("1"),stud1);
        repo.delete("1");
        assertEquals(repo.findOne("1"),null);
        repo.save(stud1);
        repo.update(stud2);
        assertEquals(repo.findOne("1"),stud2);
        repo.delete("1");
        assertEquals(repo.findAll().iterator().hasNext(),false);
    }
    @Test
    public void XMLRepotest(){
         StudentXMLRepo<String, Student<String>> repo= new StudentXMLRepo<>("XMLTests.xml");
        assertEquals(repo.findAll().iterator().hasNext(),false);
        Student<String> stud1 = new Student<>("1","A","B","123","AB");
        Student<String> stud2 = new Student<>("2","C","D","456","CD");
        repo.save(stud1);
        assertEquals(repo.findOne("1"),stud1);
        repo.save(stud2);
        assertEquals(repo.findOne("2"),stud2);
        assertEquals(repo.findAll().iterator().hasNext(),true);
        repo.delete("1");
        repo.update(new Student<>("2","Bob","Carl","123","bobby"));
        repo.delete("2");
        assertEquals(repo.findAll().iterator().hasNext(),false);
    }
}
