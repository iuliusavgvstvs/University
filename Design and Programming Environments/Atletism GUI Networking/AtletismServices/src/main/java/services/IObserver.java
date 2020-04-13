package services;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;

public interface IObserver {
    void enitityAdded(TableEntity entity) throws ValidationException;
    void personLoggedIn(User user) throws  ValidationException;
    void personLoggedOut(User user) throws ValidationException;
}
