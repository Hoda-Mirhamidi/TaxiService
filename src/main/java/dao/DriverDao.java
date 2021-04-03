package dao;

import model.entities.Driver;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DriverDao {

    public static Driver addDriver(Driver driver){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(driver);
            transaction.commit();
            return driver;
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
            return null;
        }finally {
            session.close();
        }
    }


    public static Driver lookUp(String uname){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Driver> cq = cb.createQuery(Driver.class);
        Root<Driver> root = cq.from(Driver.class);
        cq.select(root).where(cb.equal(root.get("userName"),uname));
        List drivers = session.createQuery(cq).getResultList();
        session.close();
        if(drivers.size() == 0){
            return null;
        }
        else{
            return (Driver) drivers.get(0);
        }
    }


    public static boolean updateInfo(Driver driver){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(driver);
            transaction.commit();
            return true;
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }finally {
            session.close();
        }
    }

    public static List<Driver> fetchAllDrivers(){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Driver> criteriaQuery = criteriaBuilder.createQuery(Driver.class);
        Root<Driver> root = criteriaQuery.from(Driver.class);
        criteriaQuery.select(root);
        List<Driver> drivers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return drivers;
    }


}
