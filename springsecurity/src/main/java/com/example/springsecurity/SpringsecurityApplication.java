package com.example.springsecurity;

import com.example.springsecurity.Repository.UserRepository;
import com.example.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringsecurityApplication {
	@Autowired
private UserRepository userRepository;
@PostConstruct
public void initUser(){
	List<User> userList= Stream.of(new User(101,"javatechie","password","javatechie@gmail.com"),
			new User(102,"user1","pswd1","user1@gmail.com"),
			new User(103,"user2","pswd2","user2@gmail.com"),
			new User(104,"user3","pswd3","user3@gmail.com")


	).collect(Collectors.toList());
	userRepository.saveAll(userList);

}
	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

}
