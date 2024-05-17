package com.example.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.user.entity.Role;
import com.example.user.entity.RoleEnum;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

	Optional<Role> findByName(RoleEnum name);

}