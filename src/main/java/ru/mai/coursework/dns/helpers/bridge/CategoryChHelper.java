package ru.mai.coursework.dns.helpers.bridge;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.bridge.CategoryCh;
import ru.mai.coursework.dns.entity.Characteristics;

import java.util.LinkedList;
import java.util.List;

public class CategoryChHelper {

    private SessionFactory sessionFactory;

    public CategoryChHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Characteristics> chsListByCategory(Categories category) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq
                .select(root)
                .where(cb.equal(root.get("category"), category))
        ;
        Query query = session.createQuery(cq);
        List<CategoryCh> categoryChList = query.getResultList();
        session.close();
        List<Characteristics> characteristicsList = new LinkedList<>();
        for (CategoryCh categoryCh : categoryChList) {
            characteristicsList.add(categoryCh.getCharacteristic());
        }
        return characteristicsList;
    }

    public CategoryCh categoryChByCategoryAndCharacteristic(Categories category, Characteristics ch) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq
                .select(root)
                .where(cb.and(cb.equal(root.get("category"), category), cb.equal(root.get("characteristic"), ch)))
        ;
        Query query = session.createQuery(cq);
        List<CategoryCh> categoryChList = query.getResultList();
        session.close();
        return categoryChList.get(0);
    }
}
