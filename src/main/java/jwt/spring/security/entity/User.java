package jwt.spring.security.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_table")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 //@SequenceGenerator(sequenceName = "seq_student_mtm", allocationSize = 1, name = "STUDENT_MTM_SEQ")
	@Column(name = "id")
	private Long id;
	@Column(name="username",nullable=false, unique=true)
	private String username;
	@Column(name="email",nullable=false, unique=true)
	private String email;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="roles",nullable=false)
	private String roles;
	
	public User() {
		
	}
	
	public User(long id, String username, String email, String password, String roles) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}
