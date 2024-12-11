package com.example.vipa.repository;

import com.example.vipa.model.PostAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAddressRepository extends JpaRepository<PostAddress, Integer> {

}
