package com.zosh.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.dto.LikeDto;
import com.zosh.dto.TwitDto;
import com.zosh.dto.mapper.LikeDtoMapper;
import com.zosh.dto.mapper.TwitDtoMapper;
import com.zosh.exception.LikeException;
import com.zosh.exception.TwitException;
import com.zosh.exception.UserException;
import com.zosh.model.Like;
import com.zosh.model.User;
import com.zosh.service.LikesService;
import com.zosh.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name="Like-Unlike Twit")
public class LikeController {
	
	private UserService userService;
	private LikesService likeService;
	
	public LikeController(UserService userService,LikesService likeService) {
		this.userService=userService;
		this.likeService=likeService;
	}
	
	@PostMapping("/{twitId}/like")
	public ResponseEntity<LikeDto>likeTwit(
			@PathVariable Long twitId, 
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		
		User user=userService.findUserProfileByJwt(jwt);
		Like like =likeService.likeTwit(twitId, user);
		
		LikeDto likeDto=LikeDtoMapper.toLikeDto(like,user);
		
		return new ResponseEntity<>(likeDto,HttpStatus.CREATED);
	}
	@DeleteMapping("/{twitId}/unlike")
	public ResponseEntity<LikeDto>unlikeTwit(
			@PathVariable Long twitId, 
			@RequestHeader("Authorization") String jwt) throws UserException, TwitException, LikeException{
		
		User user=userService.findUserProfileByJwt(jwt);
		Like like =likeService.unlikeTwit(twitId, user);
		
		
		LikeDto likeDto=LikeDtoMapper.toLikeDto(like,user);
		return new ResponseEntity<>(likeDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/twit/{twitId}")
	public ResponseEntity<List<LikeDto>>getAllLike(
			@PathVariable Long twitId,@RequestHeader("Authorization") String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Like> likes =likeService.getAllLikes(twitId);
		
		List<LikeDto> likeDtos=LikeDtoMapper.toLikeDtos(likes,user);
		
		return new ResponseEntity<>(likeDtos,HttpStatus.CREATED);
	}


}
