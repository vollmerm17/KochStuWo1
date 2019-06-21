package at.fh.swenga.jpa.model;

import java.util.Date;
import java.util.HashSet;

import java.util.Optional;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.apache.commons.codec.binary.Base64;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "Event")

public class EventModel {
	@Id
	@Column(name = "eventId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;

	@Column(nullable = false, length = 50)
	private String eventName;

	@Column(nullable = false, length = 250)
	private String eventDescription;

	// Date Only, no time part:
	@Temporal(TemporalType.DATE)
	private Date dayOfEvent;

	// Time Only, no date part:
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(iso = ISO.TIME)
	private Date timeOfEvent;

	@Column(nullable = false, length = 20)
	private int attendeesMax;

	@ManyToOne(cascade = CascadeType.MERGE)
	private DormModel dorm;

	@ManyToOne(cascade = CascadeType.MERGE)
	private DietModel diet;

	@ManyToOne(cascade = CascadeType.MERGE)
	private UserModel user;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<StudentModel> students;

	@OneToOne(cascade = CascadeType.ALL)
	private EventPictureModel picture;

	@OneToOne(cascade = CascadeType.ALL)
	private RecipeModel recipe;


	public EventModel() {

	}


	public EventModel(String name, String description, Date dayOfEvent, Date timeOfEvent, DormModel dorm,DietModel diet,
			int attendeesMax, UserModel user) {
		super();
		this.eventName = name;
		this.eventDescription = description;
		this.dayOfEvent = dayOfEvent;
		this.timeOfEvent = timeOfEvent;
		this.dorm = dorm;
		this.diet = diet;
		this.attendeesMax = attendeesMax;
		this.user = user;
	}




	public EventPictureModel getPicture() {
		return picture;
	}


	public void setPicture(EventPictureModel picture) {
		this.picture = picture;
	}


	public RecipeModel getRecipe() {
		return recipe;
	}


	public void setRecipe(RecipeModel recipe) {
		this.recipe = recipe;
	}


	public String getName() {
		return eventName;
	}

	public void setName(String name) {
		this.eventName = name;
	}



	public String getDescription() {
		return eventDescription;
	}



	public void setDescription(String description) {
		this.eventDescription = description;
	}



	public Date getDayOfEvent() {
		return dayOfEvent;
	}



	public void setDayOfEvent(Date dayOfEvent) {
		this.dayOfEvent = dayOfEvent;
	}



	public Date getTimeOfEvent() {
		return timeOfEvent;
	}



	public void setTimeOfEvent(Date timeOfEvent) {
		this.timeOfEvent = timeOfEvent;
	}


	public int getEventId() {
		return eventId;
	}



	public void setEventId(int eventId) {
		this.eventId = eventId;
	}



	public String getEventName() {
		return eventName;
	}



	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	public String getEventDescription() {
		return eventDescription;
	}



	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}



	public DormModel getDorm() {
		return dorm;
	}



	public void setDorm(DormModel dorm) {
		this.dorm = dorm;
	}



	public DietModel getDiet() {
		return diet;
	}



	public void setDiet(DietModel diet) {
		this.diet = diet;
	}



	public int getAttendeesMax() {
		return attendeesMax;
	}

	public void setAttendeesMax(int attendeesMax) {
		this.attendeesMax = attendeesMax;
	}


	public UserModel getUser() {
		return user;
	}


	public void setUser(UserModel user) {
		this.user = user;
	}


	public Set<StudentModel> getStudents() {
		return students;
	}



	public String getEventPicture() {

		if (picture != null) {


				StringBuilder sb = new StringBuilder();
				sb.append("data:image/png;base64,");
				sb.append(Base64.encodeBase64String(picture.getContent()));
				String image = sb.toString();
				return image;
		}
		return "img/defaultEvent.gif";
	}

	public void setStudents(Set<StudentModel> students) {
		this.students = students;
	}


	public void addStudi(StudentModel studi) {

		if (students == null) {
			students = new HashSet<StudentModel>();
		}
		if (!students.contains(studi)) {
			students.add(studi);
		}
	}


	@Override
	public String toString() {
		return "EventModel [id=" + eventId + ", name=" + eventName + ", description=" + eventDescription + ", dayOfEvent=" + dayOfEvent
				+ ", timeOfEvent=" + timeOfEvent + ", dorm=" + dorm + ", attendeesMax=" + attendeesMax
				+ ", user=" + user + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventId;
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
		if (eventId != other.eventId)
			return false;
		return true;
	}

}
