package at.fh.swenga.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "Users")
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", insertable = true, updatable = false)
	private int userId;

	// Need 3 for Spring security
	@Column(name = "userName", unique = true, nullable = false, length = 45)
	private String userName;

	@Column(name = "password", nullable = false, length = 60)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	@OneToOne(cascade = CascadeType.ALL)
	private StudentModel student;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<UserRoleModel> userRoles;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<EventModel> events;

	public UserModel() {
	}

	public UserModel(String userName, String password, boolean enabled) {
		this.userName = userName;
		this.password = password;
	}

	public UserModel(int userId, String userName, String password, boolean enabled) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	public UserModel(String userName, String password, boolean enabled, StudentModel student,
			Set<UserRoleModel> userRoles) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.student = student;
		this.userRoles = userRoles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUserRoles(Set<UserRoleModel> userRoles) {
		this.userRoles = userRoles;
	}


	public void addUserRole(UserRoleModel userRole) {
		if (userRoles == null) {
			userRoles = new HashSet<UserRoleModel>();
			userRoles.add(userRole);
		}
	}

	public Set<UserRoleModel> getUserRoles() {
		return userRoles;
	}
	
	public StudentModel getStudent() {
		return student;
	}

	public void setStudent(StudentModel student) {
		this.student = student;
	}

	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);
	}

	public Set<EventModel> getEvent() {
		return events;
	}

	public void setEvent(Set<EventModel> events) {
		this.events = events;
	}

	public void addEvent(EventModel event) {
		if (events == null) {
			events = new HashSet<EventModel>();
		}
		events.add(event);
	}
	
	@Override
	public String toString() {
		return "UserModel [userName=" + userName + ", password=" + password + ", enabled=" + enabled + ", student="
				+ student + ", userRoles=" + userRoles + "]";
	}
	

}
