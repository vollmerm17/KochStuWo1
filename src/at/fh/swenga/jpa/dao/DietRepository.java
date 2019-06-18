package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.DietModel;


@Repository

public interface DietRepository extends JpaRepository<DietModel, Integer> {

	@Transactional
	DietModel findFirstByName(String dietName);

}
