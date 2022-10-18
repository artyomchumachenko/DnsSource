package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.*;

import java.util.List;

public class CharacteristicHelper {

    private SessionFactory sessionFactory;

    public CharacteristicHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Characteristics characteristicByName(String characteristicName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Characteristics> cq = cb.createQuery(Characteristics.class);
        Root<Characteristics> root = cq.from(Characteristics.class);
        cq.select(root);
        cq.where(cb.equal(root.get("chName"), characteristicName));
        Query query = session.createQuery(cq);
        List<Characteristics> characteristics = query.getResultList();
        session.close();
        return characteristics.get(0);
    }

    public CategoryCh categoryChByParams(
            Categories category,
            Characteristics characteristic
    ) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq.select(root);
        cq.where(cb.and(cb.equal(root.get("category"), category)), cb.equal(root.get("characteristic"), characteristic));
        Query query = session.createQuery(cq);
        List<CategoryCh> categoryChList = query.getResultList();
        session.close();
        return categoryChList.get(0);
    }


}
