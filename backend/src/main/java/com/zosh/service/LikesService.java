package com.zosh.service;

import java.util.List;

import com.zosh.exception.LikeException;
import com.zosh.exception.TwitException;
import com.zosh.exception.UserException;
import com.zosh.model.Like;
import com.zosh.model.Twit;
import com.zosh.model.User;

public interface LikesService {
	
	public Like likeTwit(Long twitId, User user) throws UserException, TwitException;
	
	public Like unlikeTwit(Long twitId, User user) throws UserException, TwitException, LikeException;
	
	public List<Like> getAllLikes(Long twitId) throws TwitException;

}
