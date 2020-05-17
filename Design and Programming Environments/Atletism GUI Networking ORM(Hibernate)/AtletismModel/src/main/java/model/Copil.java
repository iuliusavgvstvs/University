package model;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;


public class Copil  implements Comparable<Copil>, Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private int age;

    public Copil(){
    }

    public Copil(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Id
    public int getId(){
        return this.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return ((Copil) o).getId() == this.getId() && ((Copil) o).getFirstName().equals(firstName) && ((Copil) o).getLastName().equals(lastName)
                && ((Copil) o).getAge() == age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), firstName, lastName, age);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getId() +
                " firstName= '" + firstName + '\'' +
                ", lastName= '" + lastName + '\'' +
                ", age= " + age;
    }

    @Override
    public int compareTo(Copil o) {
        return  1;
    }
}
