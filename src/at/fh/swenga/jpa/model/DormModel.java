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


@Entity
@Table(name = "Dorm")

public class DormModel implements Serializable {
	
	@Id
	@Column(name = "dormId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dormId;

	@Column(nullable = false, length = 50)
	private String dormName;
	
	@Column(nullable = false, length = 50)	
	private String dormStreetAndNumber;
	
	@Column(nullable = false, length = 50)
	private String dormCityAndPostalCode;
	
	
    @OneToMany(mappedBy="dorm",fetch = FetchType.LAZY)
    private Set<StudentModel> students;
    
    @OneToMany(mappedBy = "dorm", fetch = FetchType.LAZY)
	private Set<EventModel> events;
	
    public DormModel() {
    }
	

	public DormModel(String dormName, String dormStreetAndNumber, String dormCityAndPostalCode) {
		super();
		this.dormName = dormName;
		this.dormStreetAndNumber = dormStreetAndNumber;
		this.dormCityAndPostalCode = dormCityAndPostalCode;
	}
	
	public String getDormName() {
		return dormName;
	}

	public void setDormName(String dormName) {
		this.dormName = dormName;
	}

	public int getDormId() {
		return dormId;
	}


	public void setDormId(int id) {
		this.dormId = id;
	}


	public String getDormStreetAndNumber() {
		return dormStreetAndNumber;
	}


	public void setDormStreetAndNumber(String dormStreetAndNumber) {
		this.dormStreetAndNumber = dormStreetAndNumber;
	}


	public String getDormCityAndPostalCode() {
		return dormCityAndPostalCode;
	}


	public void setDormCityAndPostalCode(String dormCityAndPostalCode) {
		this.dormCityAndPostalCode = dormCityAndPostalCode;
	}


	public Set<StudentModel> getStudents() {
		return students;
	}

	public void setStudents(Set<StudentModel> students) {
		this.students = students;
	}


	public Set<EventModel> getEvents() {
		return events;
	}


	public void setEvents(Set<EventModel> events) {
		this.events = events;
	}

	

}
