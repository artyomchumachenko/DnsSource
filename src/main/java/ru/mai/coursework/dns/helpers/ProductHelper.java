package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;

import java.util.List;

public class ProductHelper {

    private SessionFactory sessionFactory;

    public ProductHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Product> getProductList() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.orderBy(cb.desc(root.get("productId")));
        Query query = session.createQuery(cq);
        List<Product> productList = query.getResultList();
        session.close();
        return productList;
    }

    public List<Product> getProductListByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.where(cb.like(root.get("productName"), "%" + name + "%"));
        Query query = session.createQuery(cq);
        List<Product> productList = query.getResultList();
        session.close();
        return productList;
    }
}
