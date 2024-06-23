import { CHANGE_THEME } from "./ActionType"

export const changeTheme=(theme)=>(dispatch)=>{

    localStorage.setItem("theme",theme)

    dispatch({type:CHANGE_THEME,payload:theme})

}