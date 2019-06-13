package at.fh.swenga.jpa.dao;
 
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.UserModel;
import at.fh.swenga.jpa.model.UserRoleModel;
 
@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	
	@Transactional
	public List<UserModel> findFirstById(int id);
	
	@Query("SELECT u FROM UserModel u ORDER BY id DESC")
	public List<UserModel> findAllId();
	

 

}