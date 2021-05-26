package pl.sda.server.database.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    public String username;

    public String codedPassword;

    public User(){}

    public User(String username, String codedPassword) {
        this.username = username;
        this.codedPassword = codedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodedPassword() {
        return codedPassword;
    }

    public void setCodedPassword(String codedPassword) {
        this.codedPassword = codedPassword;
    }
}
