package com.ECommerceProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ECommerceProject.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
