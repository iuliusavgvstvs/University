package services;

import model.Copil;
import model.Proba;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;

public interface IService {
    void login(User user, IObserver client) throws ValidationException, ChatException;
    void add(TableEntity entiy) throws  ValidationException, ChatException;
    void logout(User user, IObserver client) throws  ValidationException, ChatException;
    Copil[] findByAge(int minAge, int maxAge) throws ChatException;
    Proba[] getByCopilID(int id) throws ChatException;
}
