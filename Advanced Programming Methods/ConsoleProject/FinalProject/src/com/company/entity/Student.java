package com.company.entity;

import java.util.HashSet;
import java.util.Objects;

public class Student<ID> extends Entity<ID> {

    private String firstName, lastName, group, email;
    private HashSet<Integer> motivations = new HashSet<>();

    public Student(ID id, String firstName, String lastName, String group, String email){
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.email = email;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void motivateWeek(int week){
        motivations.add(week);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student<?> student = (Student<?>) o;
        return Objects.equals(this.getId(), student.getId());
    }

    public HashSet<Integer> getMotivations() {
        return motivations;
    }

    @Override
    public String getDetails(){
        return "Id: "+this.getId()+"  First Name: "+this.getFirstName()+"  Last Name: "+this.getLastName()+"  Group: "+this.getGroup()+"  Email: "+this.getEmail();
    }
}
