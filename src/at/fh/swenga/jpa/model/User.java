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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_ID")
	private int id;
 
	
 //Need 3 for Spring security
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String userName;
 
	@Column(name = "password", nullable = false, length = 60)
	private String password;
 
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
 
	private StudentModel student;
	
    @OneToOne(fetch = FetchType.LAZY, mappedBy="student", cascade = CascadeType.ALL)
	@JoinColumn(name = "Student_ID")
    public StudentModel getStudent() {
    	return this.student;
    }
    
    public void setStudentModel(StudentModel student) {
    	this.student = student;
    }
    
    
    
	public void setStudent(StudentModel student) {
		this.student = student;
	}



	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<UserRole> userRoles;

	public User() {
	}
 
	public User(String userName, String password, boolean enabled) {
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User(String userName, String password, boolean enabled, StudentModel student, Set<UserRole> userRoles) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.student = student;
		this.userRoles = userRoles;
	}

	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
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
 
 
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
 
	public void addUserRole(UserRole userRole) {
		if (userRoles==null) userRoles = new HashSet<UserRole>();
		userRoles.add(userRole);
	}
	
	/*
	public void addStudent(StudentModel student) {
		if (student == null) {
			student = new HashSet<StudentModel>();
		}
		students.add(student);
	}
	*/
 
 
	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);		
	}
 
}
 