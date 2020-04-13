package server;

import model.Copil;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;
import model.validator.CopilValidator;
import repository.jbdc.CopilDbRepository;
import repository.jbdc.ProbaDbRepository;
import repository.jbdc.UserDbRepository;
import services.IObserver;
import services.IService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        User useR =userRepo.getLogin(new User(1, user.getUser(), user.getPassword()));
        if (useR!=null) {
            if(loggedClients.get(user.getId())!=null)
                throw new ValidationException("User already logged in.");
            loggedClients.put(user.getId(), client);
        } else {
            throw new ValidationException("Authentication failed.");
        }
    }

    @Override
    public synchronized void add(TableEntity entiy, IObserver client) throws ValidationException {
        Copil c = new Copil(entiy.getId(), entiy.getCopilFname(), entiy.getCopilLname(), entiy.getCopilAge());
        copilValidator.validate(c);
        copilRepo.save(c);
        if(entiy.getP1()!=null)
            probaRepo.save(entiy.getP1());
        if(entiy.getP2()!=null)
            probaRepo.save(entiy.getP2());
    }

    @Override
    public void logout(User user, IObserver client) throws ValidationException {

    }
}
