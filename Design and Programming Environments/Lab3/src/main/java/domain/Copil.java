package domain;

import java.util.Objects;

public class Copil extends Entity {

    private String firstName;
    private String lastName;
    private int age;

    public Copil(int id, String firstName, String lastName, int age) {
        super(id);
        this.firstName = firstName;
        this.lastName= lastName;
        this.age= age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return ((Copil) o).getId()==this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),firstName, lastName, age);
    }

    @Override
    public String toString() {
        return  this.getId() + " '" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age;
    }
}
