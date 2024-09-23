package com.ECommerceProject.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;  // Unique identifier for each role
    
    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;  // Role name, must be unique and not empty

    @ManyToMany(mappedBy = "roles")
    private List<User> users;  // Many-to-many relationship with users


    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setName(String name) {
		this.name = name;
	}

	
    public String getName() {
        return name;  // Return the actual name of the role
    }
}
