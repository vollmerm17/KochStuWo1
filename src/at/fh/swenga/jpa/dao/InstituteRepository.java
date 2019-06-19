package at.fh.swenga.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.fh.swenga.jpa.model.InstituteModel;

@Repository
@Transactional

public interface InstituteRepository extends JpaRepository<InstituteModel, Integer> {


	InstituteModel findFirstByInstituteName(String instituteName);

}
