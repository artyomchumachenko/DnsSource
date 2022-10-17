package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;

import java.util.List;

public class ProductCategoryHelper {

    private SessionFactory sessionFactory;

    public ProductCategoryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ProductCategory> productListByCategory(Categories category) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductCategory> cq = cb.createQuery(ProductCategory.class);
        Root<ProductCategory> root = cq.from(ProductCategory.class);
        cq.select(root);
        cq.where(cb.equal(root.get("category"), category));
        Query query = session.createQuery(cq);
        // Можно ли сразу получить Product, а не ProductCategory?
        List<ProductCategory> productListByCategory = query.getResultList();
        session.close();
        return productListByCategory;
    }
}
