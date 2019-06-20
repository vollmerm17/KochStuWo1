package at.fh.swenga.jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Recipe")
public class RecipeModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String contentType;
	private Date created;
	
	private String recipeName;
	private String filename;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public byte[] getContent() {
		return content;
	}



	public void setContent(byte[] content) {
		this.content = content;
	}



	public String getContentType() {
		return contentType;
	}



	public void setContentType(String contentType) {
		this.contentType = contentType;
	}



	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}



	public String getRecipeName() {
		return recipeName;
	}



	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}


	
	
	

}
