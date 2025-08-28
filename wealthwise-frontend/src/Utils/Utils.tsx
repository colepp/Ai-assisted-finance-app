import Cookies from "universal-cookie"

export function setCookie(key: string,value: string){
    const cookies = new Cookies(null,{path: '/'});
    cookies.set(key,value);
}

export function getCookie(key:string){
    const cookies = new Cookies(null,{path: '/'});
    return cookies.get(key);
}
