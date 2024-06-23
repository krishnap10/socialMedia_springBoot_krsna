import React, { useEffect } from 'react'
import TwitCard from './TwitCard/TwitCard'
import { useDispatch, useSelector } from 'react-redux'
import { findTwitsById } from '../../../Store/Tweet/Action';
import KeyboardBackspaceIcon from "@mui/icons-material/KeyboardBackspace";
import { useNavigate, useParams } from 'react-router-dom';
import { Divider } from '@mui/material';

const TwitDetail = () => {
    const param=useParams();
    const dispatch=useDispatch();
    const {twit,theme}=useSelector(store=>store);
    const navigate=useNavigate();

    const handleBack = () => navigate(-1)

    useEffect(()=>{
        dispatch(findTwitsById(param.id))
    },[param.id])

  return (
    <div>
        <section
        className={`z-50 flex items-center sticky top-0 ${
          theme.currentTheme === "light" ? "bg-white" : "bg-[#0D0D0D]"
        } bg-opacity-95`}
      >
        <KeyboardBackspaceIcon
          className="cursor-pointer"
          onClick={handleBack}
        />
        <h1 className="py-5 text-xl font-bold opacity-90 ml-5">
          {"Twit"}
        </h1>
      </section>
       {twit.twit && <TwitCard twit={twit.twit}/>}
       <Divider sx={{margin:"2rem 0rem"}}/>

       <div>
        {twit.twit?.replyTwits.slice().reverse().map((item)=><TwitCard twit={item}/>)}
       </div>
    </div>
  )
}

export default TwitDetail