package at.fh.swenga.jpa.dao;
 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.User;
 
@Repository
@Transactional
public class UserDao {
 
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<User> findByUsername(String userName) {
		TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.userName = :name",
				User.class);
		typedQuery.setParameter("name", userName);
		List<User> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
 
	public void persist(User user) {
		entityManager.persist(user);
	}
}