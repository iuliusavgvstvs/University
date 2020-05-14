package repository;

import java.util.ArrayList;

public interface ICopilRepository<Copil>{
    Copil findOnebyName(String fname, String lname, int age);

    ArrayList<Copil> findByAge(int minAge, int maxAge);
}
