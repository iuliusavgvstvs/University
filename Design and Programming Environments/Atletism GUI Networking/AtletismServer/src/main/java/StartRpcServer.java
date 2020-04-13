import model.validator.CopilValidator;
import repository.jbdc.CopilDbRepository;
import repository.jbdc.ProbaDbRepository;
import repository.jbdc.UserDbRepository;
import server.ServiceImpl;
import services.IService;
import utils.AbstractServer;
import utils.ChatRpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        UserDbRepository userRepo = new UserDbRepository(serverProps);
        CopilDbRepository copilRepo = new CopilDbRepository(serverProps);
        ProbaDbRepository probaRepo = new ProbaDbRepository(serverProps);
        CopilValidator validator = new CopilValidator();
        IService chatServerImpl=new ServiceImpl(userRepo, copilRepo, probaRepo, validator);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
