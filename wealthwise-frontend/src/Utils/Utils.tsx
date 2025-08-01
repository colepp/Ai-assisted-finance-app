import Cookies from "universal-cookie"
import {useNavigate} from "react-router-dom";


export function setCookie(key: string,value: string){
    const cookies = new Cookies(null,{path: '/'});
    cookies.set(key,value);
}

export function getCookie(key:string){
    const cookies = new Cookies(null,{path: '/'});
    return cookies.get(key);
}

export function redirect(path:string){
    const navigator = useNavigate();
    navigator(path);
}