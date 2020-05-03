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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;


public class AtletismClientRpcReflectionWorker implements Runnable, IObserver {
    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
    private IService server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AtletismClientRpcReflectionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    //  private static Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();
    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        UserDTO udto = (UserDTO) request.data();
        User user = DTOUtils.getFromDTO(udto);
        try {
            server.login(user, this);
            return okResponse;
        } catch (ValidationException | ChatException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        System.out.println("Logout request...");
        UserDTO udto = (UserDTO) request.data();
        User user = DTOUtils.getFromDTO(udto);
        try {
            server.logout(user, this);
            connected = false;
            return okResponse;

        } catch (ValidationException | ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_EVENT(Request request) {
        System.out.println("HandleADD_EVENT ...");
        TableEntityDTO mdto = (TableEntityDTO) request.data();
        TableEntity mte = DTOUtils.getFromDTO(mdto);
        try {
            server.add(mte);
            return okResponse;
        } catch (ChatException | ValidationException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }


    }

    private Response handleFIND_BY_AGE(Request request) {
        System.out.println("FIND_BY_AGE Request ...");
        int[] a = (int[]) request.data();
        try {
            Copil[] friends = server.findByAge(a[0], a[1]);
            CopilDTO[] frDTO = DTOUtils.getDTO(friends);
            return new Response.Builder().type(ResponseType.FIND_BY_AGE).data(frDTO).build();
        } catch (ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_BY_COPIL_ID(Request request) {
        System.out.println("FIND_BY_COPIL_ID Request ...");
        try {
            int id = (int) request.data();
            Proba[] friends = server.getByCopilID(id);
            ProbaDTO[] frDTO = DTOUtils.getDTO(friends);
            return new Response.Builder().type(ResponseType.FIND_BY_COPIL_ID).data(frDTO).build();
        } catch (ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void enitityAdded(TableEntity entity) throws ValidationException, ChatException {
        TableEntityDTO mdto = DTOUtils.getDTO(entity);
        Response resp = new Response.Builder().type(ResponseType.NEW_ENTITY).data(mdto).build();
        System.out.println("Message received  " + entity);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new ChatException("Sending error: " + e);
        }
    }

}
