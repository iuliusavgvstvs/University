package dto;

import model.Copil;
import model.Proba;
import model.TableEntity;
import model.User;


public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        String id=usdto.getId();
        String pass=usdto.getPasswd();
        return new User(1,id, pass);

    }
    public static UserDTO getDTO(User user){
        String id=user.getUser();
        String pass=user.getPassword();
        return new UserDTO(id, pass);
    }





    public static Proba getFromDTO(ProbaDTO usdto){
        int id=usdto.getId();
        int CopilID = usdto.getCopilID();
        int distanta = usdto.getDistanta();
        return new Proba(id, CopilID, distanta);

    }
    public static ProbaDTO getDTO(Proba user){
        int id = user.getId();
        int CopilID = user.getCopilID();
        int distanta = user.getDistanta();
        return new ProbaDTO(id, CopilID, distanta);
    }


    public static Copil getFromDTO(CopilDTO usdto){
        int id=usdto.getId();
        String fName = usdto.getFirstName();
        String lName = usdto.getLastName();
        int age = usdto.getAge();
        return new Copil(id, fName,lName,age);

    }
    public static CopilDTO getDTO(Copil user){
        int id=user.getId();
        String fName = user.getFirstName();
        String lName = user.getLastName();
        int age = user.getAge();
        return new CopilDTO(id, fName,lName,age);
    }



    public static TableEntity getFromDTO(TableEntityDTO mdto){
        if(mdto.getP2()!=null)
        return new TableEntity(mdto.getId(), getFromDTO(mdto.getCopil()), getFromDTO(mdto.getP1()), getFromDTO(mdto.getP2()));
        else{
            return new TableEntity(mdto.getId(), getFromDTO(mdto.getCopil()), getFromDTO(mdto.getP1()), null);
        }

    }

    public static TableEntityDTO getDTO(TableEntity message){
        if(message.getP2()!=null)
        return new TableEntityDTO(message.getId(), getDTO(message.getCopil()), getDTO(message.getP1()), getDTO(message.getP2()));
        else{
            return new TableEntityDTO(message.getId(), getDTO(message.getCopil()), getDTO(message.getP1()), null);
        }
    }

    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }

    public static CopilDTO[] getDTO(Copil[] users){
        CopilDTO[] frDTO=new CopilDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static Copil[] getFromDTO(CopilDTO[] users){
        Copil[] friends=new Copil[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }



    public static ProbaDTO[] getDTO(Proba[] users){
        ProbaDTO[] frDTO=new ProbaDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static Proba[] getFromDTO(ProbaDTO[] users){
        Proba[] friends=new Proba[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
}
