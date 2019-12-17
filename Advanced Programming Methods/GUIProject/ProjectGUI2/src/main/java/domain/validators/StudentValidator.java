package domain.validators;

import domain.Student;
import domain.validators.ValidationException;

public class StudentValidator implements Validator {
    @Override
    public void validate(Object entity) throws ValidationException {
        Student st = (Student) entity;
        String id = (String) st.getId();
        if(!id.matches("[0-9]+")) {
            throw new ValidationException("Invalid id.");
        }
        if(Integer.parseInt((String) st.getId())<1){
            throw new ValidationException("Invalid student id.");
        }

        if(!Validator.isLetterAndSpace(st.getLastName())){
            throw new ValidationException("Invalid student last name.");
        }

        if(!Validator.isLetterAndSpace(st.getFirstName())){
            throw new ValidationException("Invalid student first name.");
        }

        String group = st.getGroup();
        if((!group.matches("[0-9]+")&&group.length()<1))
            throw new ValidationException("Invalid student gruop.");

        if(st.getEmail()==""){
            throw new ValidationException("Invalid student email.");
        }
    }
}
