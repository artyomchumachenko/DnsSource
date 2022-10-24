package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.CategoryCh;

import java.util.List;

public class CategoryHelper {

    private SessionFactory sessionFactory;

    public CategoryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Categories> categoryNameListById(int upCategoryId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq
                .select(root)
                .where(cb.equal(root.get("upCategoryId"), upCategoryId))
                .orderBy(cb.asc(root.get("categoryId")))
        ;
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        session.close();
        return categoriesList;
    }

    public int idByCategoryName(String categoryName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq
                .select(root)
                .where(cb.equal(root.get("categoryName"), categoryName));
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        int id = categoriesList.get(0).getCategoryId();
        session.close();
        return id;
    }

    // EDIT
    public List<CategoryCh> chListByCategory(Categories category) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq
                .select(root)
                .where(cb.equal(root.get("category"), category))
                .orderBy(cb.asc(root.get("characteristic")));
        Query query = session.createQuery(cq);
        List<CategoryCh> chList = query.getResultList();
        session.close();
        return chList;
    }

    public Categories categoryById(int categoryId) {
        Session session = sessionFactory.openSession();
        return session.getReference(Categories.class, categoryId);
    }

    public Categories categoryByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq
                .select(root)
                .where(cb.equal(root.get("categoryName"), name));
        Query query = session.createQuery(cq);
        List<Categories> categories = query.getResultList();
        session.close();
        return categories.get(0);
    }
}
