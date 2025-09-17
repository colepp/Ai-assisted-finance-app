
import {useEffect} from 'react';
import { getCookie } from '../Utils/Utils';


export default function VerifyEmail() {

  useEffect(() => {
    const sendEmail = async () => {
      console.log("Sending Verification Email");
      const authToken = getCookie("auth-token");
      console.log(authToken);
      if (authToken === null) {
        console.log("User Not Valid Or Token Expired");
        console.error("User Not Valid Or Token Expired");
      }
        try {
          console.log("attempting POST request");
          const response = await fetch("http://localhost:8080/users/register_email", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${authToken}`
            }
          });
          const data = await response.json();
          console.log("Verification email sent:", data);
        } catch (error) {
          console.error("Error sending email:", error);
        }
      };
      sendEmail();
    }, []);

  // Function to resend verification email
  // const resendVerificationEmail = async () => {
  //   console.log("Resending Verification Email");
  //   const authToken = getCookie("auth-token");
  //   if (authToken === null) {
  //     console.log("User Not Valid Or Token Expired");
  //     return;
  //   }

  //   try {
  //     const response = await fetch("http://localhost:8080/users/register_email", {
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/json",
  //         "Authorization": `Bearer ${authToken}`
  //       }
  //     });
  //     const data = await response.json();
  //     console.log("Verification email resent:", data);
  //   } catch (error) {
  //     console.error("Error resending email:", error);
  //   }
  // };

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
