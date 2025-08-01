import '../index.css'
import React, { useState, type SetStateAction } from 'react';
import { setCookie, getCookie } from '../Utils/Utils';
import '../../public/wealth-wise-log.svg'

export default function RegisterPage(){

  // Form Related
  const [email,setEmail] = useState('');
  const [firstName,setFirstName] = useState('');
  const [lastName,setLastName] = useState('');
  const [phoneNumber,setPhoneNumber] = useState('');
  const [password,setPassword] = useState('');
  const [confirmPassword,setConfirmPassword] = useState('');

  
  const [errorMessage,setErrorMessage] = useState('');


  function handleEmailChange(event: React.ChangeEvent<HTMLInputElement>){
    setEmail(event.target.value);
  }

  function handlePasswordChange(event: React.ChangeEvent<HTMLInputElement>){
    setPassword(event.target.value);
  }

  function handleConfirmPasswordChange(event: React.ChangeEvent<HTMLInputElement>){
    setConfirmPassword(event.target.value);
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
                name: `${firstName} ${lastName}`,
                phone: "phone",
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
        <section className="max-w-4xl p-6 mx-auto bg-white rounded-md shadow-md dark:bg-gray-800">
          <h2 className="text-lg font-semibold text-gray-700 capitalize dark:text-white">Account settings</h2>

          <form>
            <div className="grid grid-cols-1 gap-6 mt-4 sm:grid-cols-2">
              <div>
                <label className="text-gray-700 dark:text-gray-200" htmlFor="username">Username</label>
                <input id="username" type="text"
                       className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 dark:focus:border-blue-300 focus:outline-none focus:ring"/>
              </div>

              <div>
                <label className="text-gray-700 dark:text-gray-200" htmlFor="emailAddress">Email Address</label>
                <input id="emailAddress" type="email"
                       className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 dark:focus:border-blue-300 focus:outline-none focus:ring"/>
              </div>

              <div>
                <label className="text-gray-700 dark:text-gray-200" htmlFor="password">Password</label>
                <input id="password" type="password"
                       className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 dark:focus:border-blue-300 focus:outline-none focus:ring"/>
              </div>

              <div>
                <label className="text-gray-700 dark:text-gray-200" htmlFor="passwordConfirmation">Password
                  Confirmation</label>
                <input id="passwordConfirmation" type="password"
                       className="block w-full px-4 py-2 mt-2 text-gray-700 bg-white border border-gray-200 rounded-md dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 focus:ring-blue-300 focus:ring-opacity-40 dark:focus:border-blue-300 focus:outline-none focus:ring"/>
              </div>
            </div>

            <div className="flex justify-end mt-6">
              <button
                  className="px-8 py-2.5 leading-5 text-white transition-colors duration-300 transform bg-gray-700 rounded-md hover:bg-gray-600 focus:outline-none focus:bg-gray-600">Save
              </button>
            </div>
          </form>
        </section>
      </>
  );
}
