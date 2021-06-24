package friendsbook.model;

import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class Friend {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	@NotNull
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

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getFriendSkey() {
		return friendSkey;
	}

	public void setFriendSkey(List<String> friendSkey) {
		this.friendSkey = friendSkey;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String firstname;
	private String lastname;
	private String dob;
	private String registerdate;
	@JsonIgnore
	@OneToMany
	private List<Friend> friends = new ArrayList<>();
	@NotNull
	private String password;
	@Column
	@ElementCollection(targetClass = String.class)
	private List<String> friendSkey = new ArrayList<>();
	@NotNull
	@Column(unique = true)
	private String email;
	private String onlinestatus;
	@Column(length = 300)
	private String about;
	private String imageurl;
	private String role;
	@JsonIgnore
	@ManyToOne
	private User user;

	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Friend(int id, String firstname, String lastname, String dob, String registerdate, List<Friend> friends,
			String password, List<String> friendSkey, String email, String onlinestatus, String about, String imageurl,
			String role, User user) {
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
		this.user = user;
	}

	public Friend(User user) {
		super();
		this.id = user.getId();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.dob = user.getDob();

		this.friends = user.getFriends();

		this.email = user.getEmail();

		this.about = user.getAbout();
		this.imageurl = user.getImageurl();
		this.role = user.getRole();

	}

}
