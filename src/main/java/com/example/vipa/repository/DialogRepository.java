package com.example.vipa.repository;

import com.example.vipa.model.Client;
import com.example.vipa.model.Dialog;
import com.example.vipa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer> {
    Optional<Dialog> findByPostAndCustomer(Post post, Client customer);
}
