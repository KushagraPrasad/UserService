package com.example.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.user.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	List<User> findByMobile(String mobile);

	Optional<User> findById(String id);

	Optional<User> findByEmail(String email);

	void deleteByMobile(String mobile);

	void deleteByEmail(String email);

	List<User> findByMobileAndName(String mobile, String name);

	List<User> findByEmailAndName(String email, String name);

}
