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

public class CharacteristicHelper {

    private SessionFactory sessionFactory;

    public CharacteristicHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public Characteristics characteristicByName(String chName) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Characteristics> cq = cb.createQuery(Characteristics.class);
        Root<Characteristics> root = cq.from(Characteristics.class);
        cq
                .select(root)
                .where(cb.equal(root.get("chName"), chName));
        Query query = session.createQuery(cq);
        List<Characteristics> chs = query.getResultList();
        session.close();
        return chs.get(0);
    }
}
