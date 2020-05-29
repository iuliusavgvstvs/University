package server;

import model.Copil;
import model.Proba;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;
import model.validator.CopilValidator;
import atletism.repository.jbdc.CopilDbRepository;
import atletism.repository.jbdc.ProbaDbRepository;
import atletism.repository.jbdc.UserDbRepository;
import services.ChatException;
import services.IObserver;
import services.IService;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImpl implements IService {

    UserDbRepository userRepo;
    CopilDbRepository copilRepo;
    ProbaDbRepository probaRepo;
    CopilValidator copilValidator;
    private Map<Integer, IObserver> loggedClients;

    public ServiceImpl(UserDbRepository uRepo, CopilDbRepository cRepo, ProbaDbRepository pRepo, CopilValidator cVali){
        this.copilRepo = cRepo;
        this.userRepo = uRepo;
        this.probaRepo = pRepo;
        this.copilValidator = cVali;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, IObserver client) throws ValidationException {
        User useR =userRepo.getLogin(user);
        if (useR!=null) {
            loggedClients.put(useR.getId(), client);
        } else {
            throw new ValidationException("Authentication failed.");
        }
    }

    @Override
    public synchronized void add(TableEntity entiy) throws ValidationException, ChatException {
        Copil c = new Copil(entiy.getId(), entiy.getCopilFname(), entiy.getCopilLname(), entiy.getCopilAge());
        try {
            copilValidator.validate(c);
        }catch (ValidationException e){
            throw e;
        }
        Copil c2 = copilRepo.save(c);
        if(entiy.getP1()!=null) {
            Proba p1 = entiy.getP1();
            p1.setCopilID(c2.getId());
            probaRepo.save(p1);
        }
        if(entiy.getP2()!=null) {
            Proba p2 = entiy.getP2();
            p2.setCopilID(c2.getId());
            probaRepo.save(p2);
        }
        notifyEntityAdded(entiy);
    }

    private void notifyEntityAdded(TableEntity entity) throws ChatException, ValidationException {
        int defaultThreadsNo = 5;
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Map.Entry<Integer, IObserver> entry : loggedClients.entrySet()){
            executor.execute(() -> {
                try {
                    System.out.println("Notifying everyone for added new entity.");
                    entry.getValue().enitityAdded(entity);
                } catch (ChatException | ValidationException e) {
                    System.err.println("Error notifying friend " + e);
                }
            });
        }

        executor.shutdown();
    }

    @Override
    public void logout(User user, IObserver client) throws ValidationException, ChatException {
        User useR = userRepo.getLogin(user);
        IObserver localClient=loggedClients.remove(useR.getId());
        if (localClient==null)
            throw new ChatException("User "+user.getId()+" is not logged in.");
    }
    public synchronized Proba[] getByCopilID(int id){
        Set<Proba> result=new TreeSet<Proba>();
        for(Proba P: probaRepo.getByCopilID(id)){
            result.add(P);
        }
        if(!result.isEmpty())
            return result.toArray(new Proba[result.size()]);
        else
            return null;
    }

    public synchronized Copil[] findByAge(int minAge, int maxAge){
        Set<Copil> result=new TreeSet<Copil>();
        for(Copil C: copilRepo.findByAge(minAge, maxAge)){
            result.add(C);
        }
        if(!result.isEmpty())
         return result.toArray(new Copil[result.size()]);
        else
            return null;
    }

}
