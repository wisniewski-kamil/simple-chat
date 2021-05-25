package pl.sda.database.repositories;

import pl.sda.database.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class JpaUserRepository extends JpaRepository<User, String> implements UserRepository {
    public JpaUserRepository(EntityManagerFactory factory, Class<User> entityClass) {
        super(factory, entityClass);
    }

    @Override
    public List<User> findAllLoggedIn() {
        EntityManager em = manager();
        List<User> list = em.createQuery("select u from User u where u.isLoggedIn = true", User.class).getResultList();
        em.close();
        return list;
    }
}
