package at.fh.swenga.jpa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "Institute")

public class InstituteModel implements Serializable {


	@Id
	@Column(name = "instituteId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int instituteId;

	@Column(nullable = false, length = 50)
	private String instituteName;
	
	@Column(nullable = true, length = 50)	
	private String instituteStreetAndNumber;
	
	@Column(nullable = true, length = 50)
	private String instituteCityAndPostalCode;
	
    @OneToMany(mappedBy="institute",fetch=FetchType.LAZY)
//    @OrderBy("lastName, firstName")
    private Set<StudentModel> students;

	@Version
	long version;
    
    
    public InstituteModel() {
		// TODO Auto-generated constructor stub
    }
    
    
    
	public InstituteModel(String instituteName) {
		super();
		this.instituteName = instituteName;
	}




	public InstituteModel(String instituteName, String instituteStreetAndNumber, String instituteCityAndPostalCode) {
		super();
		this.instituteName = instituteName;
		this.instituteStreetAndNumber = instituteStreetAndNumber;
		this.instituteCityAndPostalCode = instituteCityAndPostalCode;
	}



	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public int getInstituteId() {
		return instituteId;
	}


	public void setInstituteId(int id) {
		this.instituteId = id;
	}


	public String getInstituteStreetAndNumber() {
		return instituteStreetAndNumber;
	}


	public void setInstituteStreetAndNumber(String instituteStreetAndNumber) {
		this.instituteStreetAndNumber = instituteStreetAndNumber;
	}


	public String getInstituteCityAndPostalCode() {
		return instituteCityAndPostalCode;
	}


	public void setCityAndPostalCode(String instituteCityAndPostalCode) {
		this.instituteCityAndPostalCode = instituteCityAndPostalCode;
	}


	public Set<StudentModel> getStudents() {
		return students;
	}

	public void setStudents(Set<StudentModel> students) {
		this.students = students;
	}
	
	
	@Override
	public String toString() {
		return "InstituteModel [instituteId=" + instituteId + ", instituteName=" + instituteName + ", instituteStreetAndNumber=" + instituteStreetAndNumber
				+ ", instituteCityAndPostalCode=" + instituteCityAndPostalCode + ", students=" + students + ", version=" + version + "]";
	}

	
    
}
