package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCh;

import java.util.List;

public class ProductChHelper {

    private SessionFactory sessionFactory;

    public ProductChHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ProductCh> chListByProduct(Product product) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductCh> cq = cb.createQuery(ProductCh.class);
        Root<ProductCh> root = cq.from(ProductCh.class);
        cq.where(cb.equal(root.get("product"), product));
        cq.orderBy(cb.asc(root.get("characteristic")));
        cq.select(root);
        Query query = session.createQuery(cq);
        List<ProductCh> chListByProduct = query.getResultList();
        session.close();
        return chListByProduct;
    }
}
