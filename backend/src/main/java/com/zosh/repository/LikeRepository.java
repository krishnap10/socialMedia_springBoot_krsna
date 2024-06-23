package com.zosh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zosh.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("SELECT l From Like l Where l.user.id=:userId AND l.twit.id=:twitId")
	public Like isLikeExist(@Param("userId") Long userId, @Param("twitId") Long twitId);
	
	@Query("SELECT l From Like l Where l.twit.id=:twitId")
	public List<Like> findByTwitId(@Param("twitId") Long twitId);
}
