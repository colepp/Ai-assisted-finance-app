import '../index.css'
import { useState, type SetStateAction } from 'react';
import { setCookie, getCookie } from '../Utils/Utils';
import '../../public/wealth-wise-log.svg'
import Header from "../PageComponents/Header.tsx";
import Footer from "../PageComponents/Footer.tsx";

export default function LoginPage(){

  const [email,setEmail] = useState('');
  const [password,setPassword] = useState('');
  const [errorMessage,setErrorMessage] = useState('');


  function handleEmailChange(event: React.ChangeEvent<HTMLInputElement>){
    setEmail(event.target.value);
  }

  function handlePasswordChange(event: React.ChangeEvent<HTMLInputElement>){
    setPassword(event.target.value);
  }
  
  async function loginRequest() {
    
    try{
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
                'Content-Type': 'application/json',
            },
        body: JSON.stringify({
                email: email,
                password: password,
            }),
        });
        const data = await response.json();
        console.log(data);
        setCookie('auth-token',data.token);
      }catch(e){
        console.log(e);
      }
    }
  
  return (
      <>
        <Header/>
        <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
          <div className="sm:mx-auto sm:w-full sm:max-w-sm">
            <img
                alt="wealthwise"
                src="../../public/wealth-wise-log.svg"
                className="mx-auto h-10 w-auto"
            />
            <h2 className="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">
              Sign in to your account
            </h2>
          </div>
          <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form action={loginRequest} method="POST" className="space-y-6">
              <div>
                <label htmlFor="email" className="block text-sm/6 text-gray-900 font-medium">
                  Email address
                </label>
                <div className="mt-2">
                  <input
                      value={email}
                      onChange={handleEmailChange}
                      id="email"
                      name="email"
                      type="email"
                      required
                      autoComplete="email"
                      className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
                  />
                </div>
                <div>
                  <div className="flex items-center justify-between">
                    <label htmlFor="password" className="block text-sm/6 font-medium text-gray-900">
                      Password
                    </label>
                    <div>
                      <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">
                        Forgot password?
                      </a>
                    </div>
                  </div>
                  <div className="mt-2">
                    <input
                        value={password}
                        onChange={handlePasswordChange}
                        type="password"
                        id="password"
                        name="password"
                        required
                        autoComplete='current-password'
                        className='block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6'
                    />
                  </div>
                </div>
                <div>
                  <button
                      type='submit'
                      className='mt-4 flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600'
                  >
                    Sign In
                  </button>
                </div>
              </div>
            </form>
            <p></p>
          </div>
        </div>
      </>
  );
}
