package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Categories;

import java.util.LinkedList;
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
}
