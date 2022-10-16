package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.ProductCategory;

import java.util.List;

public class ProductCategoryHelper {

    private SessionFactory sessionFactory;

    public ProductCategoryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Select all product from table @products
     * @return List
     */
    public List<ProductCategory> categoriesListByProduct(Product product) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ProductCategory> cq = cb.createQuery(ProductCategory.class);
        Root<ProductCategory> root = cq.from(ProductCategory.class);
        cq.select(root);
        cq.where(cb.equal(root.get("product"), product));
        cq.orderBy(cb.asc(root.get("productCategoryId")));
        Query query = session.createQuery(cq);
        List<ProductCategory> categoriesListByProduct = query.getResultList();
        session.close();
        return categoriesListByProduct;
    }
}
