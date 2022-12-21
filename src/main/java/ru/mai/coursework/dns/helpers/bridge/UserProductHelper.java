package ru.mai.coursework.dns.helpers.bridge;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.*;
import ru.mai.coursework.dns.entity.bridge.UserProducts;

import java.util.List;

public class UserProductHelper {

    private SessionFactory sessionFactory;

    public UserProductHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<UserProducts> userProductsByUserId(User user) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserProducts> cq = cb.createQuery(UserProducts.class);
        Root<UserProducts> root = cq.from(UserProducts.class);
        cq
                .select(root)
                .where(cb.equal(root.get("user"), user))
        ;
        Query query = session.createQuery(cq);
        List<UserProducts> userProducts = query.getResultList();
        session.close();
        return userProducts;
    }

    public List<UserProducts> userProductsByUserAndProduct(User user, Product product) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserProducts> cq = cb.createQuery(UserProducts.class);
        Root<UserProducts> root = cq.from(UserProducts.class);
        cq
                .select(root)
                .where(cb.and(cb.equal(root.get("user"), user), cb.equal(root.get("product"), product)))
        ;
        Query query = session.createQuery(cq);
        List<UserProducts> userProducts = query.getResultList();
        session.close();
        return userProducts;
    }

    public void save(UserProducts userProducts) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(userProducts);
        tx1.commit();
        session.close();
    }

    public void deleteByUserAndProduct(User user, Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        List<UserProducts> userProducts = userProductsByUserAndProduct(user, product);
        for (UserProducts up : userProducts) {
            session.remove(up);
        }
        tx1.commit();
        session.close();
    }
}
