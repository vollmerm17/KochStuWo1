package at.fh.swenga.jpa.model;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "Event")

public class EventModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 20)
	private int attendeesMax;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private StudentModel student;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
//	@OrderBy("lastName, firstName")
	private Set<PositionModel> positions;

	public EventModel() {

	}

	public EventModel(String name, int attendeesMax, StudentModel student) {
		super();
		this.name = name;
		this.attendeesMax = attendeesMax;
		this.student = student;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttendeesMax() {
		return attendeesMax;
	}

	public void setAttendeesMax(int attendeesMax) {
		this.attendeesMax = attendeesMax;
	}

	public StudentModel getStudent() {
		return student;
	}

	public void setStudent(StudentModel student) {
		this.student = student;
	}

	public Set<PositionModel> getPosition() {
		return positions;
	}

	public void setPosition(Set<PositionModel> positions) {
		this.positions = positions;
	}
	
	public void addPosition(PositionModel position) {
		if (positions==null) {
			positions= new HashSet<PositionModel>();
		}
		positions.add(position);
	}

	@Override
	public String toString() {
		return "EventModel [id=" + id + ", name=" + name + ", attendeesMax=" + attendeesMax + ", student=" + student
				+ ", positions=" + positions + "]";
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
		EventModel other = (EventModel) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

}
