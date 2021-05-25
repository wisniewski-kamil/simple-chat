package pl.sda.server.database.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class JpaRepository<T, K> implements Repository<T, K> {
    private final Class<T> entityClass;
    private final EntityManager entityManager;

    public JpaRepository(EntityManagerFactory factory, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.entityManager = factory.createEntityManager();
    }

    protected EntityManager manager(){
        return entityManager;
    }

    protected Class<T> getEntityClass(){
        return entityClass;
    }

    @Override
    public void save(T entity) {
        EntityManager em = manager();
        em.getTransaction().begin();
        try {
            em.persist(entity);
        } catch (PersistenceException ignored){
            System.err.println("Cant save entity: " + ignored.getMessage());
        }
        em.getTransaction().commit();
    }

    @Override
    public void merge(T entity) {
        EntityManager em = manager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(K id) {
        EntityManager em = manager();
        em.getTransaction().begin();
        T obj = em.find(entityClass, id);
        if (obj != null) {
            em.remove(obj);
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        EntityManager em = manager();
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(manager().find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        EntityManager em = manager();
        List<T> list = em.createQuery("select a from " + entityClass.getSimpleName() +" a", entityClass).getResultList();
        return list;
    }
}
