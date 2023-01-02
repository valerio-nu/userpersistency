package com.valerio.userpersistency.persistency.dao;

import com.valerio.userpersistency.persistency.model.RoleUser;
import com.valerio.userpersistency.persistency.model.Roles;
import com.valerio.userpersistency.persistency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles,  RoleUser> {
    List<Roles> findByUsername(String username);

    List<Roles> findByUser(User user);
}
