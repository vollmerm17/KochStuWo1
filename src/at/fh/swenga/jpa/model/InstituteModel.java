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
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = true, length = 50)	
	private String streetAndNumber;
	
	@Column(nullable = true, length = 50)
	private String cityAndPostalCode;
	
    @OneToMany(mappedBy="institute",fetch=FetchType.LAZY)
//    @OrderBy("lastName, firstName")
    private Set<StudentModel> students;

	@Version
	long version;
    
    
    public InstituteModel() {
		// TODO Auto-generated constructor stub
    }
    

	public InstituteModel(String name, String streetAndNumber, String cityAndPostalCode) {
		super();
		this.name = name;
		this.streetAndNumber = streetAndNumber;
		this.cityAndPostalCode = cityAndPostalCode;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStreetAndNumber() {
		return streetAndNumber;
	}


	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}


	public String getCityAndPostalCode() {
		return cityAndPostalCode;
	}


	public void setCityAndPostalCode(String cityAndPostalCode) {
		this.cityAndPostalCode = cityAndPostalCode;
	}


	public Set<StudentModel> getStudents() {
		return students;
	}

	public void setStudents(Set<StudentModel> students) {
		this.students = students;
	}
	
	/*
	 * public void addStudent(StudentModel student) { if (students==null) {
	 * students= new HashSet<StudentModel>(); } students.add(student); }
	 */
	
    
}
