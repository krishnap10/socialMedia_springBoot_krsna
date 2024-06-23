package com.zosh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zosh.config.JwtProvider;
import com.zosh.exception.UserException;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImplementation(
			UserRepository userRepository,
			JwtProvider jwtProvider) {
		
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		User user=userRepository.findById(userId).orElseThrow(() ->  new UserException("user not found with id "+userId));
		return user;
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		System.out.println("email"+email);
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		System.out.println("email user"+user.getEmail());
		return user;
	}

	@Override
	public User updateUser(Long userid,User req) throws UserException {
		
		User user=findUserById(userid);
		
		if(req.getFullName()!= null) {
			user.setFullName(req.getFullName());
		}
		if(req.getImage()!=null) {
			user.setImage(req.getImage());
		}
		if(req.getBackgroundImage()!=null) {
			user.setBackgroundImage(req.getBackgroundImage());
		}
		if(req.getBirthDate()!=null) {
			user.setBirthDate(req.getBirthDate());
		}
		if(req.getLocation()!=null) {
			user.setLocation(req.getLocation());
		}
		if(req.getBio()!=null) {
			user.setBio(req.getBio());
		}
		if(req.getWebsite()!=null) {
			user.setWebsite(req.getWebsite());
		}
		
		return userRepository.save(user);
		
	}

	@Override
	public User followUser(Long userId, User user) throws UserException {
		User followToUser=findUserById(userId);
		
		if(user.getFollowings().contains(followToUser) && followToUser.getFollowers().contains(user)) {
			user.getFollowings().remove(followToUser);
			followToUser.getFollowers().remove(user);
		}
		else {
					followToUser.getFollowers().add(user);
					user.getFollowings().add(followToUser);
		}
		
		userRepository.save(user);
		userRepository.save(followToUser);
		return followToUser;
	}

	@Override
	public List<User> searchUser(String query) {
	
		return userRepository.searchUser(query);
	}

}
   




