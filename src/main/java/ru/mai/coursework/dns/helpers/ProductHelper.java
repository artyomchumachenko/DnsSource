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

public class ProductHelper {

    private SessionFactory sessionFactory;

    public ProductHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Select all product from table @products
     *
     * @return List
     */
    public List<Product> productListAll(int amount) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.orderBy(cb.desc(root.get("productId")));
        Query query = session.createQuery(cq);
        List<Product> productList = query.setMaxResults(amount).getResultList();
        session.close();
        return productList;
    }

    /**
     * Select all products where equal in "name" from table @products
     *
     * @return List
     */
    public List<Product> productListByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.where(cb.like(root.get("productName"), "%" + name + "%"));
        Query query = session.createQuery(cq);
        List<Product> productListByName = query.getResultList();
        session.close();
        return productListByName;
    }

    public List<ProductCategory> productListByCategory(Categories category, int amount) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductCategory> cq = cb.createQuery(ProductCategory.class);
        Root<ProductCategory> root = cq.from(ProductCategory.class);
        cq
                .select(root)
                .where(cb.equal(root.get("category"), category));
        Query query = session.createQuery(cq);
        // Можно ли сразу получить Product, а не ProductCategory?
        List<ProductCategory> productListByCategory = query.setMaxResults(amount).getResultList();
        session.close();
        return productListByCategory;
    }
}
