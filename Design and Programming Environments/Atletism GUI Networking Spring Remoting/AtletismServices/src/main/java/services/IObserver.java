package services;

import model.TableEntity;
import model.exceptions.ValidationException;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IObserver extends  Remote {
    void enitityAdded(TableEntity entity) throws ValidationException, ChatException, RemoteException;
}
