package dao;

import model.entities.Passenger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PassengerDao {

    public static void addPassenger(Passenger passenger){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(passenger);
            transaction.commit();
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
        }finally {
            session.close();
        }
    }


    public static Passenger lookUp(String uname){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Passenger> cq = cb.createQuery(Passenger.class);
        Root<Passenger> root = cq.from(Passenger.class);
        cq.select(root).where(cb.equal(root.get("userName"),uname));
        List passengers = session.createQuery(cq).getResultList();
        session.close();
        if(passengers.size() == 0){
            return null;
        }
        else{
            return (Passenger) passengers.get(0);
        }
    }


    public static boolean updateInfo(Passenger passenger){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(passenger);
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

    public static List<Passenger> fetchAllPassengers(){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Passenger> criteriaQuery = criteriaBuilder.createQuery(Passenger.class);
        Root<Passenger> root = criteriaQuery.from(Passenger.class);
        criteriaQuery.select(root);
        List<Passenger> passengers = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return passengers;
    }
}
