package com.courses.courses.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courses.courses.domain.entities.User;
import com.courses.courses.utils.enums.RoleUser;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    public Optional<User> findByIdAndRoleUser(Long id, RoleUser roleUser);

}
