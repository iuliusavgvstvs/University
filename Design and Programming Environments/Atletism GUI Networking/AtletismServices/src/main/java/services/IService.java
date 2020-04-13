package services;

import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;

public interface IService {
    void login(User user, IObserver client) throws ValidationException;
    void add(TableEntity entiy, IObserver client) throws  ValidationException;
    void logout(User user, IObserver client) throws  ValidationException;

}
