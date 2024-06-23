import { CHANGE_THEME } from "./ActionType"

const initialState={
    currentTheme:localStorage.getItem("theme") || 'light'
}
export const themeReducer=(state=initialState,{type,payload})=>{

    if(type===CHANGE_THEME){
        return {currentTheme:payload}
    }

    return initialState;

}