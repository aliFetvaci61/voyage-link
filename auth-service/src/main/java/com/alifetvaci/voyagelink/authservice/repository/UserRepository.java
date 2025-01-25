package com.alifetvaci.voyagelink.authservice.repository;


import com.alifetvaci.voyagelink.authservice.repository.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByIdentificationNumber(String identificationNumber);
}
