package com.ecommercetech.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommercetech.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
