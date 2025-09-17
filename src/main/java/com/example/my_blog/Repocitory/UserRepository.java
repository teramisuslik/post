package com.example.my_blog.Repocitory;

import com.example.my_blog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;

public interface UserRepository extends JpaRepository<User, Long> {

}
