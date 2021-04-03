package dao;

import model.entities.Vehicle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VehicleDao {

    public static Vehicle addVehicle(Vehicle vehicle){
        Session session = HibernateUtil.sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(vehicle);
            transaction.commit();
            return vehicle;
        }catch (HibernateException exception){
            if(transaction != null){
                transaction.rollback();
            }
            return null;
        }finally {
            session.close();
        }
    }
}
