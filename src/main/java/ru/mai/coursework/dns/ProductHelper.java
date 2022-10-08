package ru.mai.coursework.dns;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.entity.Product;

import java.util.List;

public class ProductHelper {

    private SessionFactory sessionFactory;
    public ProductHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    public List<Product> getProductList() {
        Session session = sessionFactory.openSession();
        session.get(Product.class, 1L);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        List<Product> productList = query.getResultList();
        session.close();
        return productList;
    }
}
