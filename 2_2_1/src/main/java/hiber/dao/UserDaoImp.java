package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
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
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String query = "from User where car = (from Car where model = :model and series = :series)";
      User user = null;
      try {
         user = (User) sessionFactory.getCurrentSession().createQuery(query)
                 .setParameter("model", model)
                 .setParameter("series" ,series)
                 .getSingleResult();
      } catch (HibernateException e) {
         System.out.println("не получилось восстановить вользователя из БД по автомобилю");
      }
      return user;
   }

}