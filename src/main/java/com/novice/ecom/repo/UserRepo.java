package com.novice.ecom.repo;

import com.novice.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
