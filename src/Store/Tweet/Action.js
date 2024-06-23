// actions.js

import {
  TWEET_CREATE_REQUEST,
  TWEET_CREATE_SUCCESS,
  TWEET_CREATE_FAILURE,
  TWEET_DELETE_REQUEST,
  TWEET_DELETE_SUCCESS,
  TWEET_DELETE_FAILURE,
  GET_ALL_TWEETS_REQUEST,
  GET_ALL_TWEETS_SUCCESS,
  GET_ALL_TWEETS_FAILURE,
  GET_USERS_TWEET_FAILURE,
  GET_USERS_TWEET_REQUEST,
  GET_USERS_TWEET_SUCCESS,
  LIKE_TWEET_REQUEST,
  LIKE_TWEET_SUCCESS,
  LIKE_TWEET_FAILURE,
  USER_LIKE_TWEET_SUCCESS,
  USER_LIKE_TWEET_REQUEST,
  USER_LIKE_TWEET_FAILURE,
  RETWEET_CREATE_REQUEST,
  RETWEET_CREATE_SUCCESS,
  RETWEET_CREATE_FAILURE,
  FIND_TWEET_BY_ID_REQUEST,
  FIND_TWEET_BY_ID_SUCCESS,
  FIND_TWEET_BY_ID_FAILURE,
  REPLY_TWEET_REQUEST,
  REPLY_TWEET_SUCCESS,
  REPLY_TWEET_FAILURE,
} from "./ActionType";
import { api } from "../../Config/apiConfig";

export const createTweetRequest = () => ({
  type: TWEET_CREATE_REQUEST,
});

export const createTweetSuccess = (data) => ({
  type: TWEET_CREATE_SUCCESS,
  payload: data,
});

export const createTweetFailure = (error) => ({
  type: TWEET_CREATE_FAILURE,
  payload: error,
});

// Action for deleting a tweet
export const deleteTweetRequest = () => ({
  type: TWEET_DELETE_REQUEST,
});

export const deleteTweetSuccess = (tweetId) => ({
  type: TWEET_DELETE_SUCCESS,
  payload: tweetId,
});

export const deleteTweetFailure = (error) => ({
  type: TWEET_DELETE_FAILURE,
  payload: error,
});

// Action for getting all tweets
export const getAllTweetsRequest = () => ({
  type: GET_ALL_TWEETS_REQUEST,
});

export const getAllTweetsSuccess = (tweets) => ({
  type: GET_ALL_TWEETS_SUCCESS,
  payload: tweets,
});

export const getAllTweetsFailure = (error) => ({
  type: GET_ALL_TWEETS_FAILURE,
  payload: error,
});

export const getAllTweets = () => {

  return async (dispatch) => {


    dispatch(getAllTweetsRequest());
    try {
      const response = await api.get("/api/twits/");
      console.log("all tweets ",response.data)
      dispatch(getAllTweetsSuccess(response.data));
    } catch (error) {
      dispatch(getAllTweetsFailure(error.message));
    }
  };
};

export const getUsersTweets = (userId) => {
  return async (dispatch) => {
    dispatch({type:GET_USERS_TWEET_REQUEST});
    try {
      const response = await api.get(`/api/twits/user/${userId}`);
      console.log("users tweets ",response.data)
      dispatch({type:GET_USERS_TWEET_SUCCESS,payload:response.data});
    } catch (error) {
      dispatch({type:GET_USERS_TWEET_FAILURE,payload:error.message});
    }
  };
};

export const findTwitsByLikesContainUser = (userId) => {
  return async (dispatch) => {
    dispatch({type:USER_LIKE_TWEET_REQUEST});
    try {
      const response = await api.get(`/api/twits/user/${userId}/likes`);
      console.log("liked tweets ",response.data)
      dispatch({type:USER_LIKE_TWEET_SUCCESS,payload:response.data});
    } catch (error) {
      dispatch({type:USER_LIKE_TWEET_FAILURE,payload:error.message});
    }
  };
};

export const findTwitsById = (twitId) => {
  return async (dispatch) => {
    dispatch({type:FIND_TWEET_BY_ID_REQUEST});
    try {
      const response = await api.get(`/api/twits/${twitId}`);
      console.log("find tweets by id ",response.data)
      dispatch({type:FIND_TWEET_BY_ID_SUCCESS,payload:response.data});
    } catch (error) {
      dispatch({type:FIND_TWEET_BY_ID_FAILURE,payload:error.message});
    }
  };
};

export const createTweet = (tweetData) => {
  return async (dispatch) => {
    dispatch(createTweetRequest());
    try {
      const {data} = await api.post("/api/twits/create", tweetData);
      console.log("created twit ",data)
      dispatch(createTweetSuccess(data));
    } catch (error) {
      dispatch(createTweetFailure(error.message));
    }
  };
};

export const createTweetReply = (tweetData) => {
  return async (dispatch) => {
    dispatch({type:REPLY_TWEET_REQUEST});
    try {
      const {data} = await api.post("/api/twits/reply", tweetData);
      console.log("reply twit ",data)
      dispatch({type:REPLY_TWEET_SUCCESS,payload:data});
    } catch (error) {
      dispatch({type:REPLY_TWEET_FAILURE,payload:error.message});
    }
  };
};


export const createRetweet = (twitId) => {
  return async (dispatch) => {
    dispatch({ type: RETWEET_CREATE_REQUEST });
    try {
      const response = await api.put(`/api/twits/${twitId}/retwit`);
      dispatch({
        type: RETWEET_CREATE_SUCCESS,
        payload: response.data, // Assuming the response contains the created retweet data
      });
    } catch (error) {
      dispatch({
        type: RETWEET_CREATE_FAILURE,
        payload: error.message, // Or handle the error as required
      });
    }
  };
};

export const likeTweet = (twitId) => {
  return async (dispatch) => {
    dispatch({type:LIKE_TWEET_REQUEST});
    try {
      const {data} = await api.post(`/api/${twitId}/like`, {});
      console.log("like twit ",data)
      dispatch({type:LIKE_TWEET_SUCCESS,payload:data});
    } catch (error) {
      dispatch({type:LIKE_TWEET_FAILURE,payload:error.message});
    }
  };
};





export const deleteTweet = (tweetId) => {
  return async (dispatch) => {
    dispatch(deleteTweetRequest());
    try {
      await api.delete(`/api/twits/${tweetId}`);
      dispatch(deleteTweetSuccess(tweetId));
      console.log("delete twit ",tweetId)
    } catch (error) {
      dispatch(deleteTweetFailure(error.message));
    }
  };
};
