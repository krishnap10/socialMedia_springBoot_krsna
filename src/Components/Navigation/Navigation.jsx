import React from "react";
import { navigationMenu } from "../../Utils/NavigationMenu";
import { Avatar, Button, Menu, MenuItem } from "@mui/material";
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../Store/Auth/Action";
import { useNavigate } from "react-router-dom";

const Navigation = () => {
  const {auth}=useSelector(store=>store);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const openLogoutMenu = Boolean(anchorEl);
  const dispatch=useDispatch();
  const navigate=useNavigate();

  const handleOpenLogoutMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout=()=>{
    dispatch(logout())
    handleClose()
  }
  return (
    <div className="h-screen sticky top-0 ">
      <div>
        <div className="py-5">
          <img
            className="w-10"
            src="https://cdn.pixabay.com/photo/2013/07/12/15/35/community-150125_640.png"
            alt=""
          />
        </div>
        <div className="space-y-6">
          {navigationMenu.map((item) => (
            <div onClick={()=> item.title==="Profile"?navigate(`/profile/${auth.user?.id}`): navigate(`/${item.title.toLowerCase()}`)} className="cursor-pointer flex space-x-3 items-center">
              {item.icon}
              <p className="text-xl">{item.title}</p>
            </div>
          ))}
        </div>
        <div className="py-10">
          <Button
            sx={{
              width: "80%",
              borderRadius: "29px",
              py: "15px",
              bgcolor: "#25d366",
            }}
            variant="contained"
            size="large"
          >
            Ping
          </Button>
        </div>
      </div>

     <div className="flex items-center  justify-between">
     <div className="flex items-center space-x-3">
        <Avatar
          alt="Remy Sharp"
          src="https://cdn.pixabay.com/photo/2012/04/14/12/44/at-sign-33776_640.png"
        />

        <div>
          <p className="font-bold">{auth.user?.fullName}</p>
          <p className="opacity-70">@{auth.user?.fullName.split(" ")[0]}</p>
        </div>
      </div>
      <Button
        id="basic-button"
        aria-controls={openLogoutMenu ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={openLogoutMenu ? 'true' : undefined}
        onClick={handleOpenLogoutMenu}
      >
          <MoreHorizIcon/>
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={openLogoutMenu}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        <MenuItem onClick={handleLogout}>Logout</MenuItem>
      </Menu>
    
     </div>
    </div>
  );
};

export default Navigation;
