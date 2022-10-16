package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Categories;

import java.util.List;

public class CategoryHelper {

    private SessionFactory sessionFactory;

    public CategoryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Select categories list from table @categories
     * @return List
     */
    public List<Categories> categoryListAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("categoryId")));
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        session.close();
        return categoriesList;
    }
}
