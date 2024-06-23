import HomeIcon from '@mui/icons-material/Home';
import ExploreIcon from '@mui/icons-material/Explore';
import NotificationsNoneIcon from '@mui/icons-material/NotificationsNone';
import MessageIcon from '@mui/icons-material/Message';
import ListAltIcon from '@mui/icons-material/ListAlt';
import GroupIcon from '@mui/icons-material/Group';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import MoreVertIcon from '@mui/icons-material/MoreVert';

export const navigationMenu=[
    {
        title:"Home",
        icon:<HomeIcon/>,
        path:"/home"
    },
    
   /* {
        title: "Notifications",
        icon:<NotificationsNoneIcon />,
        path:"/notifications"
    },

    // {
    //     title:"Messages" ,
    //     icon:<MessageIcon/>,
    //     path:"/messages"
    // },
    // {
    //     title:"Lists" ,
    //     icon:<ListAltIcon/>,
    //     path:"/lists"
    // },

   /* {
        title:"Channels" ,
        icon:<GroupIcon/>,
        path:"/channels"
    },*/

    {
        title: "Premium",
        icon:<HowToRegIcon/>,
        path:"/premium"
    },
    {
        title:"Profile" ,
        icon:<AccountBoxIcon/>,
        path:"/profile"
        
    },
    {
        title:"More" ,
        icon:<MoreVertIcon/>,
        path:"/more"
    },
]