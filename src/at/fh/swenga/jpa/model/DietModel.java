package at.fh.swenga.jpa.model;

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

public class DietModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String restriction;
	
    @OneToMany(mappedBy="diet",fetch=FetchType.LAZY)
    //@OrderBy("lastName, firstName")
    private Set<StudentModel> students;
    
    public DietModel(){
    	
    }
    
    
	public DietModel(String name, String restriction) {
		super();
		this.name = name;
		this.restriction = restriction;
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
		DietModel other = (DietModel) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DietModel [id=" + id + ", name=" + name + ", restriction=" + restriction + ", students=" + students
				+ "]";
	}

	
}
