package org.datacollector.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
 
@Entity
@SequenceGenerator(name="Userr_SEQ", sequenceName="Userr_SEQ", initialValue=1, allocationSize=1)
public class Userr {
 
	private static final long serialVersionUID = -519076103343422121L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Userr_SEQ")
	private Long id;
 
    @Column(nullable=false)
    private String password;
 
    @Column(nullable=false)
    private String email;
 
    @Column(nullable=false)
    private UserProfileType role;
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
  
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    
    
    public UserProfileType getRole() {
		return role;
	}

	public void setRole(UserProfileType role) {
		this.role = role;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.intValue();
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Userr))
            return false;
        Userr other = (Userr) obj;
        if (id != other.id)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "Userr [id=" + id + ", password=" + password + ", email=" + email + "]";
	}
 

    
 
     
}