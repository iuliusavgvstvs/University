package utils;

import rpcprotocol.AtletismClientRpcReflectionWorker;
import services.IService;

import java.net.Socket;


public class AltetismRpcConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public AltetismRpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        AtletismClientRpcReflectionWorker worker=new AtletismClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
