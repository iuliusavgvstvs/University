package dto;

import java.io.Serializable;


public class CopilDTO implements Serializable{
    private String firstName;
    private String lastName;
    private int age, id;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public CopilDTO(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


}
