package at.fh.swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.StudentModel;
import at.fh.swenga.jpa.model.UserModel;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserModel, Integer> {

	@Transactional

	public UserModel findUserByUserName(String userName);

	@Query("SELECT u FROM UserModel u ORDER BY id DESC")
	public List<UserModel> findAllId();

	UserModel findFirstByUserName(String userName);

	StudentModel findStudentByUserId(UserModel user);

	@Query("select u from UserModel u where u.userName = :name")
	List<UserModel> findByUserName(@Param("name") String userName);
	
	@Query("SELECT s FROM UserModel s WHERE s.userName != 'administrator' AND s.enabled = 1")
	List<UserModel> findAllWithoutAdmin();
	
	public UserModel findUserByUserId(int id);

	UserModel findFirstByUserId(int id);
	

}
