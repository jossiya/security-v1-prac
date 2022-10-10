package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository 라는 어노테이션이 없어도 IOC되요. JpaRepository를 상속했기 때문에..
public interface UserRepository extends JpaRepository<User,Long> {
  Optional <User> findByUsername(String username);
}
