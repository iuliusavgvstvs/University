package services;

import model.TableEntity;
import model.exceptions.ValidationException;

public interface IObserver {
    void enitityAdded(TableEntity entity) throws ValidationException, ChatException;
}
