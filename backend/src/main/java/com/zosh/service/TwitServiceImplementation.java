package com.zosh.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springdoc.core.converters.models.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zosh.exception.TwitException;
import com.zosh.exception.UserException;
import com.zosh.model.Twit;
import com.zosh.model.User;
import com.zosh.repository.TwitRepository;
import com.zosh.request.TwitReplyRequest;
import com.zosh.request.TwitRequest;

@Service
public class TwitServiceImplementation implements TwitService {
	
	private TwitRepository twitRepository;
	
	public TwitServiceImplementation(TwitRepository twitRepository) {
		this.twitRepository=twitRepository;
	}

	@Override
	public Twit createTwit(Twit req,User user) {
		
		
		Twit twit=new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setVideo(req.getVideo());
		
		
		return twitRepository.save(twit);
	}

	@Override
	public Twit retwit(Long twitId, User user) throws TwitException {
		Twit twit=findById(twitId);
		if(twit.getRetwitUser().contains(user)) {
			twit.getRetwitUser().remove(user);
		}
		else {
			twit.getRetwitUser().add(user);
		}
		
		return twitRepository.save(twit);
	}

	@Override
	public Twit findById(Long twitId) throws TwitException {
		
		Twit twit=twitRepository.findById(twitId)
				.orElseThrow(()-> new TwitException("Twit Not Found With Id "+twitId));
		
		return twit;
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
		Twit twit=findById(twitId);
		
		if(!userId.equals(twit.getUser().getId())) {
			throw new UserException("you can't delete another users twit");
		}
		twitRepository.deleteById(twit.getId());
		
		
	}

	@Override
	public Twit removeFromRetwit(Long twitId, User user) throws TwitException, UserException {
		
		Twit twit=findById(twitId);
	
		twit.getRetwitUser().remove(user);
		
		return twitRepository.save(twit);
	}

	@Override
	public Twit createReply(TwitReplyRequest req,User user) throws TwitException {
		// TODO Auto-generated method stub
		
		Twit twit=findById(req.getTwitId());
		
		Twit reply=new Twit();
		reply.setContent(req.getContent());
		reply.setCreatedAt(LocalDateTime.now());
		reply.setImage(req.getImage());
		reply.setUser(user);
		reply.setReplyFor(twit);
		reply.setReply(true);
		reply.setTwit(false);
		
		
		
		Twit savedReply= twitRepository.save(reply);
		
		twit.getReplyTwits().add(savedReply);
		twitRepository.save(twit);
		return twit;
	}

	@Override
	public List<Twit> findAllTwit() {
//		 Sort sortByCreatedAtDesc = org.springframework.data.domain.Sort.Order("DESC")
		return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public List<Twit> getUsersTwit(User user) {
		
		return twitRepository.findByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Twit> findByLikesContainsUser(User user) {
		return twitRepository.findByLikesUser_Id(user.getId());
	}
	
	

}
