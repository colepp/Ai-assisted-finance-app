import '../index.css'
import React, {use, useState} from 'react';
import {setCookie, getCookie, redirect} from '../Utils/Utils';
import '../../public/wealth-wise-log.svg'
import Footer from "../PageComponents/Footer.tsx";
import Header from "../PageComponents/Header.tsx";
import {useNavigate} from "react-router-dom";

export default function RegisterPage(){

    const navigate = useNavigate();

  // Form Related
  const [email,setEmail] = useState('');
  const [firstName,setFirstName] = useState('');
  const [lastName,setLastName] = useState('');
  const [phoneNumber,setPhoneNumber] = useState('');
  const [password,setPassword] = useState('');
  const [confirmPassword,setConfirmPassword] = useState('');

  
  const [errorMessage,setErrorMessage] = useState('');

  function handleFirstNameChange(event: React.ChangeEvent<HTMLInputElement>){
      setFirstName(event.target.value)
  }

  function handleLastNameChange(event: React.ChangeEvent<HTMLInputElement>){
      setLastName(event.target.value)
  }

  function handleEmailChange(event: React.ChangeEvent<HTMLInputElement>){
    setEmail(event.target.value);
  }

  function handlePhoneChange(event: React.ChangeEvent<HTMLInputElement>){
      setPhoneNumber(event.target.value)
  }

  function handlePasswordChange(event: React.ChangeEvent<HTMLInputElement>){
    setPassword(event.target.value);
  }

  function handleConfirmPasswordChange(event: React.ChangeEvent<HTMLInputElement>){
    setConfirmPassword(event.target.value);
  }
  
  async function handleSignUp() {
      const areaCode = document.getElementById('area-code')
    
    try{
      const response = await fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: {
                'Content-Type': 'application/json',
            },
        body: JSON.stringify({
                email: email,
                name: `${firstName} ${lastName}`,
                phone_number: `${areaCode.value} ${phoneNumber}`,
                password: password,
                confirm_password: confirmPassword
            }),
        });
        const data = await response.json();
        console.log(data);
        setCookie('auth-token',data.token);
        navigate("/verify");
      }catch(e){
        console.log(e);
      }
    }

  
  return (
      <>
          <Header/>
          <h2 className="text-center mt-5 text-xl font-medium text-purple-600">Sign Up</h2>
          <div className="flex justify-center mt-5 mb-5">
              <form className="flex flex-col items-center w-full max-w-md shadow-lg p-6 rounded-lg"
                    action={handleSignUp}>
                  <label htmlFor="first-name" className="w-full">
                      <span className="text-sm font-medium text-gray-700">First Name</span>
                      <input
                          type="text"
                          value={firstName}
                          onChange={handleFirstNameChange}
                          id="first-name"
                          className="mt-0.5 w-full rounded border-gray-300 shadow-sm sm:text-sm"
                      />
                  </label>
                  <label htmlFor="last-name" className="w-full">
                      <span className="text-sm font-medium text-gray-700">Last Name</span>
                      <input
                          type="text"
                          id="last-name"
                          onChange={handleLastNameChange}
                          className="mt-0.5 w-full rounded border-gray-300 shadow-sm sm:text-sm"
                      />
                  </label>
                  <label htmlFor="email" className="w-full">
                      <span className="text-sm font-medium text-gray-700">Email</span>
                      <input
                          type="email"
                          id="email"
                          value={email}
                          onChange={handleEmailChange}
                          className="mt-0.5 w-full rounded border-gray-300 shadow-sm sm:text-sm"
                      />
                  </label>
                  <label htmlFor="password" className="w-full mt-4">
                      <span className="text-sm font-medium text-gray-700">Password</span>
                      <input
                          type="password"
                          id="password"
                          value={password}
                          onChange={handlePasswordChange}
                          className="mt-0.5 w-full rounded border-gray-300 shadow-sm sm:text-sm"
                      />
                  </label>
                  <label htmlFor="confirm-password" className="w-full mt-4">
                      <span className="text-sm font-medium text-gray-700">Confirm Password</span>
                      <input
                          type="password"
                          id="confirm-password"
                          value={confirmPassword}
                          onChange={handleConfirmPasswordChange}
                          className="mt-0.5 w-full rounded border-gray-300 shadow-sm sm:text-sm"
                      />
                  </label>
                  <label htmlFor="phone" className="w-full mt-4">
                      <span className="text-sm font-medium text-gray-700">Phone Number</span>
                      <div className="flex mt-0.5">
                          <select
                              id="area-code"
                              // value={areaCode}
                              className="mr-2 rounded border-gray-300 shadow-sm sm:text-sm"
                          >
                              <option value="+1">+1 (US)</option>
                              <option value="+44">+44 (UK)</option>
                              <option value="+61">+61 (AU)</option>
                              <option value="+81">+81 (JP)</option>
                              <option value="+91">+91 (IN)</option>
                          </select>
                          <input
                              type="tel"
                              value={phoneNumber}
                              onChange={handlePhoneChange}
                              id="phone"
                              className="w-full rounded border-gray-300 shadow-sm sm:text-sm"
                          />
                      </div>
                  </label>
                  <button
                      type="submit"
                      className="mt-4 rounded-sm bg-indigo-600 px-8 py-3 text-sm font-medium text-white transition hover:scale-110 hover:shadow-xl focus:ring-3 focus:outline-none"
                  >
                      Submit
                  </button>
              </form>
          </div>
          <Footer/>
      </>
  );
}
