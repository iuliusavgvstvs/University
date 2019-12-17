package com.company.validator;

import com.company.entity.Homework;
import com.company.exceptions.ValidationException;

public class HomeworkValidator implements Validator {
    @Override
    public void validate(Object entity) throws ValidationException {
        Homework h = (Homework) entity;

        String id = (String) h.getId();
        if(!id.matches("[0-9]+")) {
            throw new ValidationException("Invalid id.");
        }

        if(Integer.parseInt((String) h.getId())<1){
            throw new ValidationException("Invalid student id.");
        }

        if(h.getStartWeek()>h.getDeadlineWeek()){
            throw new ValidationException("Homework start week bigger than deadline week.");
        }
        if(h.getStartWeek()<1||h.getStartWeek()>14){
            throw new ValidationException("Invalid homework start week.");
        }
        if(h.getDeadlineWeek()<1||h.getDeadlineWeek()>14){
            throw new ValidationException("Invalid homework deadline week.");
        }
        if(h.getDescription()==""){
            throw new ValidationException("Invalid homework description.");
        }
    }
}
