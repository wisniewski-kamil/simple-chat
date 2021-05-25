package pl.sda.server.database.repositories;

import pl.sda.server.database.entities.User;

import java.util.List;

public interface UserRepository extends Repository<User, String> {
    List<User> findAllLoggedIn();
}
