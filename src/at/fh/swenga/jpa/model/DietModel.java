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
@Table(name = "Diet")

public class DietModel implements Serializable{

	@Id
	@Column(name = "dietId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dietId;

	@Column(nullable = false, length = 50)
	private String dietName;

	@Column(nullable = false, length = 50)
	private String restriction;

	@OneToMany(mappedBy = "diet", fetch = FetchType.LAZY)
	private Set<EventModel> events;
	
    @OneToMany(mappedBy="diet",fetch=FetchType.LAZY)
    private Set<StudentModel> students;

    public DietModel(){

    }


	public DietModel(String dietName, String restriction) {
		super();
		this.dietName = dietName;
		this.restriction = restriction;
	}

	public int getDietId() {
		return dietId;
	}


	public void setDietId(int id) {
		this.dietId = id;
	}


	public String getDietName() {
		return dietName;
	}


	public void setDietName(String dietName) {
		this.dietName = dietName;
	}


	public String getRestriction() {
		return restriction;
	}


	public void setRestriction(String restriction) {
		this.restriction = restriction;
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
