
import React, {useEffect} from 'react';
import {createCookie} from "react-router-dom";


export default function VerifyEmail() {


    useEffect(() => {
        const sendEmail =
    },[])


  return (
    <div className="flex flex-col min-h-screen bg-gray-100 dark:bg-gray-900">
      <main className="flex-grow flex items-center justify-center">
        <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8 text-center">
          <h1 className="text-3xl font-bold text-gray-900 dark:text-white sm:text-4xl">
            Verify Your Email
          </h1>
          <p className="mt-4 max-w-md mx-auto text-lg text-gray-600 dark:text-gray-300">
            We've sent a verification link to your email address. Please check your inbox (and spam/junk folder) to confirm your account.
          </p>
          <a
            href="/"
            className="mt-8 inline-block rounded-md bg-indigo-600 px-6 py-3 text-white font-medium hover:bg-indigo-500 transition"
          >
            Return to Homepage
          </a>
          <div className="mt-6">
            <p className="text-sm text-gray-500 dark:text-gray-400">
              Didn't receive the email?{' '}
              <a
                href="/resend-verification"
                className="text-indigo-600 dark:text-indigo-400 hover:underline"
              >
                Click here to resend
              </a>
            </p>
          </div>
        </div>
      </main>
    </div>
  );
};
