package domain.validator;

import domain.Copil;
import domain.exceptions.ValidationException;

import javax.xml.validation.Validator;

public class CopilValidator implements IValidator<Copil> {
    @Override
    public void validate(Copil entity) throws ValidationException {
        if(!IValidator.isLetterAndSpace(entity.getFirstName())){
            throw  new ValidationException("Invalid first name");
        }
        if(!IValidator.isLetterAndSpace(entity.getLastName())){
            throw  new ValidationException("Invalid last name");
        }
        if(entity.getAge()<6||entity.getAge()>15)
            throw new ValidationException("Invalid age");
    }
}
