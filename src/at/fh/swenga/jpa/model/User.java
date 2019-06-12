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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)


	@Column(name = "id", insertable = true, updatable = false)

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
	public StudentModel getStudent() {
    	return this.student;
    }
    
    public void setStudentModel(StudentModel student) {
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
 
	public void addUserRole(UserRole userRole) {
		if (userRoles==null) userRoles = new HashSet<UserRole>();
		userRoles.add(userRole);
	}
 
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
 
	public void encryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = passwordEncoder.encode(password);		
	}
 
}
 