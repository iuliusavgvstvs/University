package rpcprotocol;


import dto.*;
import model.Copil;
import model.Proba;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;
import services.ChatException;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class AtletismServicesRpcProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AtletismServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    public void login(User user, IObserver client) throws ChatException {
        initializeConnection();
        UserDTO udto = DTOUtils.getDTO(user);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(udto).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new ChatException(err);
        }
    }

    @Override
    public void add(TableEntity message) throws ChatException {
        TableEntityDTO mdto = DTOUtils.getDTO(message);
        Request req = new Request.Builder().type(RequestType.ADD_EVENT).data(mdto).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ChatException(err);
        }
    }

    public void logout(User user, IObserver client) throws ChatException {
        UserDTO udto = DTOUtils.getDTO(user);
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(udto).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ChatException(err);
        }
    }

    @Override
    public Copil[] findByAge(int minAge, int maxAge) throws ChatException {
        int[] a = {minAge, maxAge};
        Request req = new Request.Builder().type(RequestType.FIND_BY_AGE).data(a).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ChatException(err);
        }
        CopilDTO[] frDTO = (CopilDTO[]) response.data();
        Copil[] friends = DTOUtils.getFromDTO(frDTO);
        return friends;
    }

    @Override
    public Proba[] getByCopilID(int id) throws ChatException {

        Request req = new Request.Builder().type(RequestType.FIND_BY_COPIL_ID).data(id).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new ChatException(err);
        }
        ProbaDTO[] frDTO = (ProbaDTO[]) response.data();
        Proba[] friends = DTOUtils.getFromDTO(frDTO);
        return friends;
    }


    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws ChatException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ChatException("Error sending object " + e);
        }

    }

    private Response readResponse() throws ChatException {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ChatException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response) {

        if (response.type() == ResponseType.NEW_ENTITY) {
            TableEntity message = DTOUtils.getFromDTO((TableEntityDTO) response.data());
            try {
                client.enitityAdded(message);
            } catch (ChatException | ValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.NEW_ENTITY;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
