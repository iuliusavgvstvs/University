package service;

import domain.User;
import domain.exceptions.ValidationException;
import domain.validator.UserValidator;
import repository.UserDbRepository;

public class UserService implements IService<User> {
    UserValidator validator;
    UserDbRepository reposiotry;

    public UserService(UserValidator val, UserDbRepository repo){
        this.validator = val;
        this.reposiotry = repo;
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public User save(User entity) throws ValidationException {
        validator.validate(entity);
        return reposiotry.save(entity);
    }

    public boolean getLogin(User entity) throws ValidationException {
        return reposiotry.getLogin(entity);
    }
}
