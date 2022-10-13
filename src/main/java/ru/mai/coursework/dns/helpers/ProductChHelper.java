package ru.mai.coursework.dns.helpers;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.Characteristics;

import java.util.List;

public class ProductChHelper {

    private SessionFactory sessionFactory;

    public ProductChHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Select characteristic list from table @characteristics
     *
     * @return List
     */
    public List<Characteristics> getCharacteristicList() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Characteristics> cq = cb.createQuery(Characteristics.class);
        Root<Characteristics> root = cq.from(Characteristics.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("chName")));
        Query query = session.createQuery(cq);
        List<Characteristics> chs = query.getResultList();
        session.close();
        return chs;
    }
}
