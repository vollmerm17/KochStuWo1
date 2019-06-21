package at.fh.swenga.jpa.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.StudentModel;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<StudentModel, Integer> {

	StudentModel findStudentByEmail(String email);

	StudentModel findStudentByUser(int id);

	StudentModel findFirstById(int id);

	StudentModel findFirstByFirstName(String firstName);

	StudentModel findStudentByUserUserId(int userId);

	List<StudentModel> removeById(int id);

	@Query("SELECT s FROM StudentModel s WHERE s.firstName != 'admin'")
	List<StudentModel> findAllWithoutAdmin();

}
