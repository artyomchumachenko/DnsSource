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
import ru.mai.coursework.dns.entity.CategoryCh;

import java.util.LinkedList;
import java.util.List;

public class CategoryHelper {

    private SessionFactory sessionFactory;

    public CategoryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Select categories list from table @categories
     *
     * @return List
     */
    public List<String> categoryNameListById(int upCategoryId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("categoryId")));
        cq.where(cb.equal(root.get("upCategoryId"), upCategoryId));
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        List<String> categoriesNameList = new LinkedList<>();
        String upCat;
        if (upCategoryId != 0) {
            upCat = upCategoryNameByUpId(upCategoryId);
            categoriesNameList.add(upCat);
        }
        for (Categories cat : categoriesList) {
            categoriesNameList.add(cat.getCategoryName());
        }
        session.close();
        return categoriesNameList;
    }

    public int idByCategoryName(String categoryName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq.select(root).where(cb.equal(root.get("categoryName"), categoryName));
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        int id = categoriesList.get(0).getCategoryId();
        session.close();
        return id;
    }

    public List<CategoryCh> chListByCategory(Categories category) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq.select(root).where(cb.equal(root.get("category"), category));
        Query query = session.createQuery(cq);
        List<CategoryCh> chList = query.getResultList();
        session.close();
        return chList;
    }

    private String upCategoryNameByUpId(int upCategoryId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Categories> cq = cb.createQuery(Categories.class);
        Root<Categories> root = cq.from(Categories.class);
        cq.select(root).where(cb.equal(root.get("categoryId"), upCategoryId));
        Query query = session.createQuery(cq);
        List<Categories> categoriesList = query.getResultList();
        if (!categoriesList.isEmpty()) {
            return categoriesList.get(0).getCategoryName();
        } else {
            return "";
        }
    }

    public Categories categoryById(int categoryId) {
        Session session = sessionFactory.openSession();
        return session.getReference(Categories.class, categoryId);
    }
}
