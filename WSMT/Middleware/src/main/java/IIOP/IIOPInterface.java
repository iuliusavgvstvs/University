package IIOP;


import Domain.Course;
import Domain.GradeEntry;
import Domain.Student;

import java.rmi.*;
import java.util.List;

public interface IIOPInterface extends Remote {
    List<GradeEntry> getCatalog() throws RemoteException;
    List<Student> getStudents() throws RemoteException;
    List<Course> getCourses() throws RemoteException;
    int addStudent(String name) throws RemoteException;
    int updateStudent(int id, String name) throws RemoteException;
    int deleteStudent(int id) throws RemoteException;
    int addCourse(String name) throws RemoteException;
    int updateCourse(int id, String name) throws RemoteException;
    int deleteCourse(int id) throws RemoteException;
    int addGrade(int studentId, int courseId, int grade) throws RemoteException;
    int updateGrade(int id, int grade)  throws  RemoteException;
}

