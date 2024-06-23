package com.zosh.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.dto.TwitDto;
import com.zosh.dto.mapper.TwitDtoMapper;
import com.zosh.exception.TwitException;
import com.zosh.exception.UserException;
import com.zosh.model.Twit;
import com.zosh.model.User;
import com.zosh.request.TwitReplyRequest;
import com.zosh.request.TwitRequest;
import com.zosh.response.ApiResponse;
import com.zosh.service.TwitService;
import com.zosh.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/twits")
@Tag(name="Twit Management", description = "Endpoints for managing twits")
public class TwitController {
	
	private TwitService twitService;
	private UserService userService;
	
	public TwitController(TwitService twitService,UserService userService) {
		this.twitService=twitService;
		this.userService=userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req, 
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		
		System.out.println("content + "+req.getContent());
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.createTwit(req, user);
		
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit,user);
		
		return new ResponseEntity<>(twitDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/reply")
	public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest req, 
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		
		
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.createReply(req, user);
		
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit,user);
		
		return new ResponseEntity<>(twitDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{twitId}/retwit")
	public ResponseEntity<TwitDto> retwit( @PathVariable Long twitId,
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Twit twit=twitService.retwit(twitId, user);
		
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit,user);
		
		return new ResponseEntity<>(twitDto,HttpStatus.OK);
	}
	
	@GetMapping("/{twitId}")
	public ResponseEntity<TwitDto> findTwitById( @PathVariable Long twitId, 
			@RequestHeader("Authorization") String jwt) throws TwitException, UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.findById(twitId);
		
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit,user);
		
		return new ResponseEntity<>(twitDto,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{twitId}")
	public ResponseEntity<ApiResponse> deleteTwitById( @PathVariable Long twitId,
		@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		twitService.deleteTwitById(twitId, user.getId());
		
		ApiResponse res=new ApiResponse();
		res.setMessage("twit deleted successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TwitDto>> findAllTwits(@RequestHeader("Authorization") String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		List<Twit> twits=twitService.findAllTwit();
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits,user);
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TwitDto>> getUsersTwits(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) 
			throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User user=userService.findUserById(userId);
		List<Twit> twits=twitService.getUsersTwit(user);
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits,reqUser);
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId,
			@RequestHeader("Authorization") String jwt) 
			throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User user=userService.findUserById(userId);
		List<Twit> twits=twitService.findByLikesContainsUser(user);
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits,reqUser);
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}

}
