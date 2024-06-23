package com.zosh.service;

import java.util.List;

import com.zosh.exception.TwitException;
import com.zosh.exception.UserException;
import com.zosh.model.Twit;
import com.zosh.model.User;
import com.zosh.request.TwitReplyRequest;
import com.zosh.request.TwitRequest;

public interface TwitService {
	
	
	public Twit createTwit(Twit req,User user)throws UserException, TwitException;
	
	public List<Twit> findAllTwit();
	
	public Twit retwit(Long twitId, User user) throws UserException, TwitException;
	
	public Twit findById(Long twitId) throws TwitException;
	
	public void deleteTwitById(Long twitId,Long userId) throws TwitException, UserException;
	
	public Twit removeFromRetwit(Long twitId, User user) throws TwitException, UserException;
	
	public Twit createReply(TwitReplyRequest req,User user) throws TwitException;
	
	public List<Twit> getUsersTwit(User user);
	
	public List<Twit> findByLikesContainsUser(User user);
	
	

}
