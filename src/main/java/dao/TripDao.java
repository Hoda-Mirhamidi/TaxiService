package dao;

import model.entities.Driver;
import model.entities.Trip;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TripDao {

    public static Trip addTrip(Trip trip){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(trip);
            transaction.commit();
            return trip;
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
            return null;
        }finally {
            session.close();
        }
    }

    public static Trip lookUp(Driver driver){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);
        Root<Trip> root = cq.from(Trip.class);
        cq.select(root).where(cb.equal(root.get("driver").get("id"),driver.getId()));
        List trips = session.createQuery(cq).getResultList();
        session.close();
        if(trips.size() == 0){
            return null;
        }
        else{
            return (Trip) trips.get(0);
        }
    }

    public static void updateInfo(Trip trip){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.update(trip);
            transaction.commit();
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
        }finally {
            session.close();
        }
    }

    public static boolean cancel(Trip trip){

        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.delete(trip);
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

    public static List<Trip> fetchAllTrips(){
        Session session = HibernateUtil.sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = criteriaBuilder.createQuery(Trip.class);
        Root<Trip> root = criteriaQuery.from(Trip.class);
        criteriaQuery.select(root);
        List<Trip> trips = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return trips;
    }

}
