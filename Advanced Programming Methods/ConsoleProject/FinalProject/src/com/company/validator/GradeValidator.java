package com.company.validator;

import com.company.entity.Grade;
import com.company.exceptions.ValidationException;

public class GradeValidator implements Validator {
    @Override
    public void validate(Object entity) throws ValidationException {
        Grade st = (Grade) entity;
        String stid= (String) st.getStudentId();
        String hid =(String) st.getHomeworkID();

        if(!stid.matches("[0-9]+")) {
            throw new ValidationException("Invalid grade id.");
        }
        if(!hid.matches("[0-9]+")) {
            throw new ValidationException("Invalid grade id.");
        }

        if(!Validator.isLetterAndSpace(st.getProf())){
            throw new ValidationException("Invalid grade teacher.");
        }

    }

}
