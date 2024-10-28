package com.mcsv.auth_server.Repository;

import com.mcsv.auth_server.Entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser,Integer> {

    Optional<AuthUser> findByUserName(String userName);

}
