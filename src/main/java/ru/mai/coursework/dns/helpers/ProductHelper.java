package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.*;
import ru.mai.coursework.dns.entity.bridge.ProductCategory;
import ru.mai.coursework.dns.entity.bridge.ProductCh;

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
    public List<Product> productListByName(String name, int amount) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        cq.where(cb.like(root.get("productName"), "%" + name + "%"));
        Query query = session.createQuery(cq);
        List<Product> productListByName = query.setMaxResults(amount).getResultList();
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

    public List<ProductCh> productChListByFilters(
            Categories category, int amount,
            int amountFilters,
            List<Characteristics> characteristicsList,
            List<String> valueList
    ) {
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
        CriteriaBuilder cb1 = session.getCriteriaBuilder();
        CriteriaQuery<ProductCh> cq1 = cb1.createQuery(ProductCh.class);
        Root<ProductCh> root1 = cq1.from(ProductCh.class);
        for (ProductCategory productCategory : productListByCategory) {
            for (int j = 0; j < amountFilters; j++) {
                cq1
                        .select(root1)
                        .where(cb1.equal(root1.get("product"), productCategory.getProduct()))
                        .where(cb1.equal(root1.get("characteristic"), characteristicsList.get(j)))
                        .where(cb1.equal(root1.get("chValue"), valueList.get(j)));
            }
        }
        Query query1 = session.createQuery(cq1);
        List<ProductCh> resultProductList = query1.setMaxResults(amount).getResultList();
        session.close();
        return resultProductList;
    }
}
