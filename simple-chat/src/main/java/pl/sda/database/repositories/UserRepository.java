package pl.sda.database.repositories;

import pl.sda.database.entities.User;

import java.util.List;

public interface UserRepository extends Repository<User, String> {
    List<User> findAllLoggedIn();
}
