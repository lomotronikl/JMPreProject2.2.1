package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      Session session = sessionFactory.getCurrentSession();
      session.save(user);
      user.getCar().setUser(user);
      session.save(user.getCar()) ;

   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public List<User> listUsers(String carModel, int carSeries) {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("select u from User u inner join u.car where u.car.model=:model and u.car.series=:series ");
      query.setParameter("model",carModel);
      query.setParameter("series", carSeries);
      return query.getResultList();
   }


}
