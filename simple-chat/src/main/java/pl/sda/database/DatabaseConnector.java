package pl.sda.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnector {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("chat");
}
