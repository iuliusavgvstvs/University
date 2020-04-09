package domain.validator;

import domain.User;
import domain.exceptions.ValidationException;

public class UserValidator implements IValidator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getUser().length() < 4)
            throw new ValidationException("username too short");
        if (entity.getPassword().length() < 6)
            throw new ValidationException("password too short");

    }
}
