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
import ru.mai.coursework.dns.entity.Characteristics;
import ru.mai.coursework.dns.entity.VariantsCategoryCh;

import java.util.List;

public class CharacteristicHelper {

    private SessionFactory sessionFactory;

    public CharacteristicHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public CategoryCh categoryChByName(String characteristicName) {
        Characteristics characteristic = characteristicByName(characteristicName);
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CategoryCh> cq = cb.createQuery(CategoryCh.class);
        Root<CategoryCh> root = cq.from(CategoryCh.class);
        cq
                .select(root)
                .where(cb.equal(root.get("characteristic"), characteristic));
        Query query = session.createQuery(cq);
        List<CategoryCh> catChList = query.getResultList();
        session.close();
        for (CategoryCh categoryCh : catChList) {
            System.out.println(categoryCh.getCategoryChId());
        }
        return catChList.get(0);
    }

    public Characteristics characteristicByName(String characteristicName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Characteristics> cq = cb.createQuery(Characteristics.class);
        Root<Characteristics> root = cq.from(Characteristics.class);
        cq
                .select(root)
                .where(cb.equal(root.get("characteristicName"), characteristicName));
        Query query = session.createQuery(cq);
        List<Characteristics> chs = query.getResultList();
        session.close();
        return chs.get(0);
    }
}
