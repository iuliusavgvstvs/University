package domain.validator;

import domain.Proba;
import domain.exceptions.ValidationException;

public class ProbaValidator implements IValidator<Proba> {
    @Override
    public void validate(Proba entity) throws ValidationException {
        if(entity.getCopilID()<0)
            throw new ValidationException("Invalid CopilID");
        int distance = entity.getDistanta();
        if(distance!=50 && distance!=100 && distance!= 1000 && distance !=1500)
            throw new ValidationException("Invalid distance");

    }
}
