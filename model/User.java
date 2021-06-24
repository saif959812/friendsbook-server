package friendbook.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
@Entity

public class User {

	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String firstname, String lastname, String dob, String registerdate, List<Friend> friends,
			String password, List<String> friendSkey, String email, String onlinestatus, String about,
			String imageurl, String role) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.registerdate = registerdate;
		this.friends = friends;
		this.password = password;
		this.friendSkey = friendSkey;
		this.email = email;
		this.onlinestatus = onlinestatus;
		this.about = about;
		this.imageurl = imageurl;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getRegisterdate() {
		return registerdate;
	}
	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}
	public List<Friend> getFriends() {
		return friends;
	}
	public void setFriends(List<Friend>friends) {
		this.friends=friends;
	}
	public String getPassword() {
		return password;
	}@JsonProperty(access = Access.WRITE_ONLY)
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getFriendSkey() {
		return friendSkey;
	}
	public void setFriendSkey(List<String> friendSkey) {
		this.friendSkey=friendSkey;
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOnlinestatus() {
		return onlinestatus;
	}
	public void setOnlinestatus(String onlinestatus) {
		this.onlinestatus = onlinestatus;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	@NotNull
	private String firstname;
	private String lastname;
	private String dob;
	private String registerdate;
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY ,mappedBy = "user")

	private List<Friend> friends=new ArrayList<>();
	@NotNull
	@JsonIgnore
	private String password;
	@JsonIgnore
	@Column
    @ElementCollection(targetClass=String.class)
	private List<String> friendSkey=new ArrayList<>(); 
	@NotNull
	@Column(unique = true)
	private String email;
	private String onlinestatus;
	@Column(length = 300)
	private String about;
	private String imageurl;
	private String role;

}
