package ru.mai.coursework.dns.helpers.bridge;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.coursework.dns.HibernateUtil;
import ru.mai.coursework.dns.entity.bridge.CategoryCh;
import ru.mai.coursework.dns.entity.bridge.ChValues;

import java.util.List;

public class ChValuesHelper {

    private SessionFactory sessionFactory;

    public ChValuesHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<ChValues> valuesListByCategoryCh(CategoryCh categoryCh) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ChValues> cq = cb.createQuery(ChValues.class);
        Root<ChValues> root = cq.from(ChValues.class);
        cq
                .select(root)
                .where(cb.equal(root.get("categoryCh"), categoryCh))
        ;
        Query query = session.createQuery(cq);
        List<ChValues> valuesList = query.getResultList();
        session.close();
        return valuesList;
    }
}
