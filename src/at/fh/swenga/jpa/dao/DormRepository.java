package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.DormModel;

@Repository

public interface DormRepository extends JpaRepository<DormModel, Integer> {

	@Transactional
	DormModel findFirstByName(String dormName);

}
