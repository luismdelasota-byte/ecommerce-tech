package com.ecommercetech.profileUser.repository;

import com.ecommercetech.profileUser.model.ProfileUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {
}
