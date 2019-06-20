package at.fh.swenga.jpa.model;
 
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Version;
 
@Entity
@Table(name = "ProfilePicture")
public class ProfilePictureModel implements java.io.Serializable {
 
	@Id
	@Column(name = "pictureId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pictureId;
 
	private String pictureName;
	private String pictureDescription;
	private String filename;
 
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
 
	private String contentType;
	private Date created;
 
	@Version
	long version;
 
	public int getId() {
		return pictureId;
	}
 
	public void setId(int id) {
		this.pictureId = id;
	}
 
	public String getName() {
		return pictureName;
	}
 
	public void setName(String name) {
		this.pictureName = name;
	}
 
	public String getDescription() {
		return pictureDescription;
	}
 
	public void setDescription(String description) {
		this.pictureDescription = description;
	}
 
	public String getFilename() {
		return filename;
	}
 
	public void setFilename(String filename) {
		this.filename = filename;
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
 
}
