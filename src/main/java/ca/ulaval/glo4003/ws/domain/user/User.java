package ca.ulaval.glo4003.ws.domain.user;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private String email;
    private String password;

    public User(String email, String password){
        this.email = email;
        hashPassword(password);
    }

    public String getEmail(){
        return this.email;
    }

    public boolean isPasswordValid(String password){
        return BCrypt.checkpw(password, this.password);
    }

    public String generateToken(){
        return "";
    }

    private void hashPassword(String password){
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

}