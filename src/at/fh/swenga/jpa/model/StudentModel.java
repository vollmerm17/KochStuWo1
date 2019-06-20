package at.fh.swenga.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Student")
public class StudentModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id ;


	@Column(nullable = false, length = 30)
	private String firstName;

	@Column(nullable = false, length = 30)
	private String lastName;

	@Column(nullable = true, length = 50)
	private String streetAndNumber;

	@Column(nullable = true, length = 50)
	private String cityAndPostalCode;

	@Column(nullable = true, length = 20)
	private String phoneNumber;

	// Date Only, no time part:
	@Temporal(TemporalType.DATE)
	private Date dayOfBirth;

	@Column(nullable = false, length = 40)
	private String email;

	@Column(nullable = false, length = 1)
	private String gender;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private InstituteModel institute;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private DietModel diet;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private DormModel dorm;

	@OneToOne(cascade = CascadeType.ALL)
	private ProfilePictureModel picture;
	

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	// @OrderBy("lastName, firstName")
	private Set<PositionModel> positions;
	


	@OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserModel user;

    
    public UserModel getUser() {
    	return this.user;
    }

    public void setUser(UserModel user) {
    	this.user = user;
    }



	public StudentModel() {
	}
	
	

	public StudentModel(String firstName, String lastName, String streetAndNumber, String cityAndPostalCode,
			String phoneNumber, Date dayOfBirth, String email, String gender, InstituteModel institute, DietModel diet,
			DormModel dorm) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAndNumber = streetAndNumber;
		this.cityAndPostalCode = cityAndPostalCode;
		this.phoneNumber = phoneNumber;
		this.dayOfBirth = dayOfBirth;
		this.email = email;
		this.gender = gender;
		this.institute = institute;
		this.diet = diet;
		this.dorm = dorm;
	}

	public StudentModel(int id, String firstName, String lastName, String streetAndNumber, String cityAndPostalCode,
			String phoneNumber, Date dayOfBirth, String email, String gender, InstituteModel institute, DietModel diet,
			DormModel dorm) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAndNumber = streetAndNumber;
		this.cityAndPostalCode = cityAndPostalCode;
		this.phoneNumber = phoneNumber;
		this.dayOfBirth = dayOfBirth;
		this.email = email;
		this.gender = gender;
		this.institute = institute;
		this.diet = diet;
		this.dorm = dorm;
	}


	public StudentModel(int id, String firstName, String lastName, String streetAndNumber, String cityAndPostalCode,
			String phoneNumber, Date dayOfBirth, String email, String gender, InstituteModel institute, DietModel diet,
			DormModel dorm, UserModel user) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAndNumber = streetAndNumber;
		this.cityAndPostalCode = cityAndPostalCode;
		this.phoneNumber = phoneNumber;
		this.dayOfBirth = dayOfBirth;
		this.email = email;
		this.gender = gender;
		this.institute = institute;
		this.diet = diet;
		this.dorm = dorm;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public InstituteModel getInstitute() {
		return institute;
	}

	public void setInstitute(InstituteModel institute) {
		this.institute = institute;
	}

	public DietModel getDiet() {
		return diet;
	}

	public void setDiet(DietModel diet) {
		this.diet = diet;
	}

	public DormModel getDorm() {
		return dorm;
	}

	public void setDorm(DormModel dorm) {
		this.dorm = dorm;
	}

	public Set<PositionModel> getPositions() {
		return positions;
	}

	public void setPositions(Set<PositionModel> positions) {
		this.positions = positions;
	}

	public void addPosition(PositionModel position) {
		if (positions == null) {
			positions = new HashSet<PositionModel>();
		}
		positions.add(position);
	}








	public ProfilePictureModel getPicture() {
		return picture;
	}

	public void setPicture(ProfilePictureModel picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentModel other = (StudentModel) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StudentModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", streetAndNumber="
				+ streetAndNumber + ", cityAndPostalCode=" + cityAndPostalCode + ", phoneNumber=" + phoneNumber
				+ ", dayOfBirth=" + dayOfBirth + ", email=" + email + ", gender=" + gender + ", institute=" + institute
				+ ", diet=" + diet + ", dorm=" + dorm +  ", positions=" + positions + "]";
	}


}
