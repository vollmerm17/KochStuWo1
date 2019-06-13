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
	
	List<StudentModel> findStudentByEmail(String email);
	/*
	 * List<StudentModel> findByLastName(String lastName);
	 * 
	 * List<StudentModel> findByFirstName(String firstName);
	 * 
	 * @Query("select e from StudentModel e where LOWER(e.firstName) LIKE CONCAT('%',LOWER(:name), '%') or e.lastName = :name"
	 * ) List<StudentModel> findByWhateverName(@Param("name") String name);
	 * 
	 * List<StudentModel> doANameSearchWithLike(@Param("search") String
	 * searchString);
	 * 
	 * int countByLastName(String lastname);
	 * 
	 * List<StudentModel> removeByLastName(String lastname);
	 * 
	 * int deleteByInstituteName(String lastname);
	 * 
	 * List<StudentModel>
	 * findByLastNameContainingOrFirstNameContainingAllIgnoreCase(String lastName,
	 * String firstName);
	 * 
	 * public List<StudentModel> findByOrderByLastNameAsc();
	 * 
	 * public List<StudentModel> findAllByOrderByLastNameAsc();
	 * 
	 * public List<StudentModel> findTop10ByOrderByLastName();
	 * 
	 * @Query("SELECT e " + "FROM StudentModel AS e " + "JOIN e.university AS c " +
	 * "WHERE c.name = :name " + "OR e.lastName = :name " +
	 * "ORDER BY e.lastName ASC")
	 * 
	 * public List<StudentModel>
	 * findByInstituteNameOrderByLastNameAsc(@Param("name") String searchString);
	 * 
	 * public List<StudentModel> findByDayOfEnrollmentAfter(Calendar date);
	 * 
	 * public List<StudentModel> findByDayOfEnrollmentBetween(Calendar dateFrom,
	 * Calendar dateTo);
	 * 
	 * List<StudentModel> findByInstituteName(String instituteName);
	 */
}
