package start;

import model.Proba;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.ProbaClient;
import atletism.rest.ServiceException;

public class StartRestClient {

    private final static ProbaClient probaClient = new ProbaClient();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Proba proba = new Proba(2, 50, 100);
        try {
            show(()-> System.out.println(probaClient.create(proba)));
            show(()->{
                Proba[] res = probaClient.getAll();
                for(Proba p:res){
                    System.out.println(p.getId()+" "+p.getCopilID()+" "+p.getDistanta());
                }
            });
        }catch (RestClientException ex){
            System.out.println("Exceptie..."+ex.getMessage());
        }
    }
        private static void show(Runnable task) {
            try {
                task.run();
            } catch (ServiceException e) {
                //  LOG.error("Service exception", e);
                System.out.println("Service exception"+ e);
            }
    }
}
