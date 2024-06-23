import React, { useState } from "react";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { Avatar, Button, Menu, MenuItem } from "@mui/material";
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import RepeatIcon from "@mui/icons-material/Repeat";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import BarChartIcon from "@mui/icons-material/BarChart";
import { useDispatch, useSelector } from "react-redux";
import { createRetweet, deleteTweet, likeTweet } from "../../../../Store/Tweet/Action";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { useLocation, useNavigate } from "react-router-dom";
import ReplyModal from "./ReplyModal";

const TwitCard = ({ twit }) => {
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);
  const [isLiked, setIsLiked] = useState(twit.liked);
  const [likes, setLikes] = useState(twit.totalLikes);
  const [isRetwit, setIsRetwit] = useState(
    twit.retwitUsersId.includes(auth.user.id)
  );
  const [retwit, setRetwit] = useState(twit.totalRetweets);
  const [openReplyModel, setOpenReplyModel] = useState();
  const location = useLocation();
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = React.useState(null);
  const openDeleteMenu = Boolean(anchorEl);
  const handleOpenDeleteMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseDeleteMenu = () => {
    setAnchorEl(null);
  };

  const handleLikeTweet = (num) => {
    dispatch(likeTweet(twit.id));
    setIsLiked(!isLiked);
    setLikes(likes + num);
  };
  const handleCreateRetweet = () => {
    dispatch(createRetweet(twit.id));
    setRetwit(isRetwit ? retwit - 1 : retwit + 1);
    setIsRetwit(!retwit);
  };
  const handleCloseReplyModel = () => setOpenReplyModel(false);

  const handleOpenReplyModel = () => setOpenReplyModel(true);
  const handleNavigateToTwitDetial = () => navigate(`/twit/${twit.id}`);

  const handleDeleteTwit = () => {
    dispatch(deleteTweet(twit.id))
    handleCloseDeleteMenu();
  };
 
  return (
    <div className="">
      {auth.user?.id !== twit.user.id &&
        location.pathname === `/profile/${auth.user?.id}` && (
          <div className="flex items-center font-semibold text-gray-700 py-2">
            <RepeatIcon />
            <p className="ml-3">Your Reping</p>
          </div>
        )}
      <div className="flex space-x-5 ">
        <Avatar
          onClick={() => navigate(`/profile/${twit.user.id}`)}
          alt="Avatar"
          src={twit.user.image}
          className="cursor-pointer"
        />
        <div className="w-full">
          <div className="flex justify-between items-center ">
            <div
              onClick={() => navigate(`/profile/${twit.user.id}`)}
              className="flex cursor-pointer items-center space-x-2"
            >
              <span className="font-semibold">{twit.user.fullName}</span>
              <span className=" text-gray-600">
                @{twit.user.fullName.toLowerCase().split(" ").join("_")} · 2m
              </span>
              {twit.user.verified && (
                <img
                  className="ml-2 w-5 h-5"
                  src="https://abs.twimg.com/responsive-web/client-web/verification-card-v2@3x.8ebee01a.png"
                  alt=""
                />
              )}
            </div>
            <div>
              <Button onClick={handleOpenDeleteMenu}>
                 <MoreHorizIcon
                id="basic-button"
                aria-controls={openDeleteMenu ? "basic-menu" : undefined}
                aria-haspopup="true"
                aria-expanded={openDeleteMenu ? "true" : undefined}
               
              />
              </Button>
             
              <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={openDeleteMenu}
                onClose={handleCloseDeleteMenu}
                MenuListProps={{
                  "aria-labelledby": "basic-button",
                }}
              >
               {twit.user.id ===auth.user.id &&  <MenuItem onClick={handleDeleteTwit}>Delete</MenuItem>}
                <MenuItem onClick={()=>navigate(`/twit/${twit.id}`)}>Details</MenuItem>
              </Menu>
            </div>
          </div>

          <div className="mt-2 ">
            <div
              className="cursor-pointer"
              onClick={handleNavigateToTwitDetial}
            >
              <p className="mb-2 p-0 ">{twit.content}</p>
             { twit.image && <img
                className="w-[28rem] border border-gray-400 p-5 rounded-md"
                src={twit.image}
                alt=""
              />}
             {twit.video &&  <div className="flex flex-col items-center w-full border border-gray-400 rounded-md">
<video className="max-h-[40rem] p-5"  controls src={twit.video}/>
              </div>}
              
            </div>

            <div className="py-5 flex flex-wrap justify-between items-center">
              <div className="space-x-3 flex items-center text-gray-600">
                <ChatBubbleOutlineIcon
                  className="cursor-pointer"
                  onClick={handleOpenReplyModel}
                />
                {twit.totalReplies > 0 && <p>{twit.totalReplies}</p>}
              </div>
              <div
                className={`${
                  isRetwit ? "text-pink-600" : "text-gray-600"
                } space-x-3 flex items-center`}
              >
                <RepeatIcon
                  className={` cursor-pointer`}
                  onClick={handleCreateRetweet}
                />
                {retwit > 0 && <p>{retwit}</p>}
              </div>
              <div
                className={`${
                  isLiked ? "text-pink-600" : "text-gray-600"
                } space-x-3 flex items-center `}
              >
                {isLiked ? (
                  <FavoriteIcon onClick={() => handleLikeTweet(-1)} />
                ) : (
                  <FavoriteBorderIcon onClick={() => handleLikeTweet(1)} />
                )}
                {likes > 0 && <p>{likes}</p>}
              </div>
              <div className="space-x-3 flex items-center text-gray-600">
                <BarChartIcon />
                <p>24,599</p>
              </div>
              <div className="flex items-center text-gray-600">
                <FileUploadIcon />
              </div>
            </div>
          </div>
        </div>
      </div>
      <ReplyModal
        twitData={twit}
        open={openReplyModel}
        handleClose={handleCloseReplyModel}
      />
    </div>
  );
};

export default TwitCard;
