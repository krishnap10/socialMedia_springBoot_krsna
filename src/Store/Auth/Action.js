// authActions.js
import axios from "axios";
import {
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  REGISTER_FAILURE,
  GET_PROFILE_REUEST,
  GET_PROFILE_SUCCESS,
  LOGOUT,
  UPDATE_USER_REQUEST,
  UPDATE_USER_SUCCESS,
  UPDATE_USER_FAILURE,
  FIND_USER_BY_ID_REQUEST,
  FIND_USER_BY_ID_FILURE,
  FIND_USER_BY_ID_SUCCESS,
  GOOGLE_LOGIN_REQUEST,
  GOOGLE_LOGIN_SUCCESS,
  GOOGLE_LOGIN_FAILURE,
  FOLLOW_USER_REQUEST,
  FOLLOW_USER_SUCCESS,
  FOLLOW_USER_FAILURE,
  SEARCH_USER_REQUEST,
  SEARCH_USER_SUCCESS,
  SEARCH_USER_FAILURE,
} from "./ActionType";
import { API_BASE_URL, api } from "../../Config/apiConfig";

export const loginRequest = () => ({
  type: LOGIN_REQUEST,
});

export const loginSuccess = (userData) => ({
  type: LOGIN_SUCCESS,
  payload: userData,
});

export const loginFailure = (error) => ({
  type: LOGIN_FAILURE,
  payload: error,
});

export const registerRequest = () => ({
  type: REGISTER_REQUEST,
});

export const registerSuccess = (userData) => ({
  type: REGISTER_SUCCESS,
  payload: userData,
});

export const registerFailure = (error) => ({
  type: REGISTER_FAILURE,
  payload: error,
});

const getUserProfileRequest = () => ({
  type: GET_PROFILE_REUEST,
});
const getUserProfileSuccess = (user) => ({
  type: GET_PROFILE_SUCCESS,
  payload: user,
});
const getUserProfileFailure = (error) => ({
  type: GET_PROFILE_SUCCESS,
  payload: error,
});

export const loginUser = (loginData) => async (dispatch) => {
  dispatch(loginRequest());
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signin`, loginData);
    const user = response.data;
    console.log("login user -: ", user);
    if (user.jwt) {
      localStorage.setItem("jwt", user.jwt);
    }
    dispatch(loginSuccess(user));
  } catch (error) {
    dispatch(loginFailure(error.message || "An error occurred during login."));
  }
};

export const loginWithGoogleAction = (data) => async (dispatch) => {
  dispatch({type:GOOGLE_LOGIN_REQUEST});
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signin/google`, data);
    const user = response.data;
    console.log("login with google user -: ", user);
    if (user.jwt) {
      localStorage.setItem("jwt", user.jwt);
    }
    dispatch({type:GOOGLE_LOGIN_SUCCESS,payload:user.jwt});
  } catch (error) {
    dispatch({type:GOOGLE_LOGIN_FAILURE, payload: error.message || "An error occurred during login."});
  }
};

// /signin/google

export const registerUser = (userData) => async (dispatch) => {
  dispatch(registerRequest());
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signup`, userData);
    const user = response.data;
    console.log("created user - : ", user);
    if (user.jwt) {
      localStorage.setItem("jwt", user.jwt);
    }
    dispatch(registerSuccess(user));
  } catch (error) {
    dispatch(
      registerFailure(error.message || "An error occurred during registration.")
    );
  }
};

export const getUserProfile = (jwt) => async (dispatch) => {
  dispatch(getUserProfileRequest());
  try {
    const response = await axios.get(`${API_BASE_URL}/api/users/profile`,{
      headers:{
        "Authorization":`Bearer ${jwt}`,

      }
    });
    const user = response.data;
    console.log("login user -: ", user);
   
    dispatch(getUserProfileSuccess(user));
  } catch (error) {
    dispatch(
      getUserProfileFailure(error.message || "An error occurred during login.")
    );
  }
};

export const findUserById = (userId) => async (dispatch) => {
  dispatch({type:FIND_USER_BY_ID_REQUEST})
  try {
    const response = await api.get(`/api/users/${userId}`);
    const user = response.data;
    console.log("find by id user -: ", user);
   
    dispatch({type:FIND_USER_BY_ID_SUCCESS,payload:user});
  } catch (error) {
    dispatch(
      {type:FIND_USER_BY_ID_FILURE,error:error.message}
    );
  }
};

export const searchUser = (query) => async (dispatch) => {
  dispatch({type:SEARCH_USER_REQUEST})
  try {
    const response = await api.get(`/api/users/search?query=${query}`);
    const users = response.data;
    console.log("search result -: ", users);
   
    dispatch({type:SEARCH_USER_SUCCESS,payload:users});
  } catch (error) {
    dispatch(
      {type:SEARCH_USER_FAILURE,error:error.message}
    );
  }
};
    
export const updateUserProfile = (reqData) => async (dispatch) => {
  console.log("updatte profile reqData",reqData)
  dispatch({type:UPDATE_USER_REQUEST})
  try {
    const response = await api.put(`/api/users/update`,reqData);
    const user = response.data;
    console.log("updated user -: ", user);
   
    dispatch({type:UPDATE_USER_SUCCESS,payload:user});
  } catch (error) {
    dispatch({type:UPDATE_USER_FAILURE,payload:error.message});
  }
};

export const FollowUserAction = (userId) => async (dispatch) => {
  // console.log("updatte profile reqData",reqData)
  dispatch({type:FOLLOW_USER_REQUEST})
  try {
    const response = await api.put(`/api/users/${userId}/follow`);
    const user = response.data;
    console.log("follow user -: ", user);
   
    dispatch({type:FOLLOW_USER_SUCCESS,payload:user});
  } catch (error) {
    console.log("catch error ",error)
    dispatch({type:FOLLOW_USER_FAILURE,payload:error.message});
  }
};

export const logout = (navigate) => (dispatch) => {
  // navigate("/")
  localStorage.removeItem("jwt");
  dispatch({ type: LOGOUT, payload: null });
};
