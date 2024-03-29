package IIOP;


import Domain.Course;
import Domain.GradeEntry;
import Domain.Student;
import Repository.CourseDao;
import Repository.GradeDao;
import Repository.GradeEntryDao;
import Repository.StudentDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

public class IIOPServer extends PortableRemoteObject implements IIOPInterface{
    private static final long serialVersionUID = 1L;

    protected IIOPServer() throws RemoteException {
    }

    @Override
    public List<GradeEntry> getCatalog() throws RemoteException {
        return GradeEntryDao.getAllGradesEntry();
    }

    @Override
    public List<Student> getStudents() throws RemoteException {
        return StudentDao.getAllStudents();
    }

    @Override
    public List<Course> getCourses() throws RemoteException {
        return CourseDao.getAllCourses();
    }

    @Override
    public int addStudent(String name) throws RemoteException {
        System.out.println("name "+ name);
        return StudentDao.saveStudent(name);
    }

    @Override
    public int updateStudent(int id, String name) throws RemoteException {
        return StudentDao.updateStudent(id, name);
    }

    @Override
    public int deleteStudent(int id) throws RemoteException {
        return StudentDao.removeStudent(id);
    }

    @Override
    public int addCourse(String name) throws RemoteException {
        return CourseDao.saveCourse(name);
    }

    @Override
    public int updateCourse(int id, String name) throws RemoteException {
        return CourseDao.updateCourse(id, name);
    }

    @Override
    public int deleteCourse(int id) throws RemoteException {
        return CourseDao.removeCourse(id);
    }

    @Override
    public int addGrade(int studentId, int courseId, int grade) throws RemoteException {
        return GradeDao.saveGrade(studentId,courseId,grade);
    }

    @Override
    public int updateGrade(int id, int grade) throws RemoteException {
        return GradeDao.updateGrade(id, grade);
    }

    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
            props.setProperty("java.naming.provider.url", "iiop://localhost/Catalog");
            Context ctx = new InitialContext(props);
            ctx.rebind("Catalog", new IIOPServer());
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
