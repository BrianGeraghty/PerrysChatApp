package com.example.PerrysChatApp.Repositories;

import com.example.PerrysChatApp.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
