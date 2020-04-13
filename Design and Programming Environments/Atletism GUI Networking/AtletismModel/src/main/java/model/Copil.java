package model;

import java.util.Objects;

public class Copil extends Entity {

    private String firstName;
    private String lastName;
    private int age;

    public Copil(int id, String firstName, String lastName, int age) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    @Override
    public String toString() {
        return this.getId() +
                " firstName= '" + firstName + '\'' +
                ", lastName= '" + lastName + '\'' +
                ", age= " + age;
    }
}
