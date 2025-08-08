package mate.academy.dao.impl;

import mate.academy.dao.UserDao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public User get(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User create(String email, String password, String salt) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist();
        }
    }
}
