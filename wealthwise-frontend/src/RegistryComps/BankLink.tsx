import Header from "../PageComponents/Header.tsx";
import Footer from "../PageComponents/Footer.tsx";
import { useEffect } from "react";

export default function BankLink()
{

    useEffect(() => {
        const linkToken = async () => {
            try {
                const data = await fetch("http://localhost:8080/plaid/api/create_link_token",{
                    method: "POST",
                    headers: {
                        "user-id": "4",
                        "Content-Type": "application/json",
                    }
                });
            }catch{ console.error('API call failed');} 
        };
        linkToken();
    }, []);


    return(
        <>
            <Header/>
            <section className="bg-white lg:grid lg:h-screen lg:place-content-center dark:bg-gray-900">
                <div className="mx-auto w-screen max-w-screen-xl px-4 py-16 sm:px-6 sm:py-24 lg:px-8 lg:py-32">
                    <div className="mx-auto max-w-prose text-center">
                        <h1 className="text-4xl font-bold text-gray-900 sm:text-5xl dark:text-white">
                            You're one step closer to a
                            <strong className="text-indigo-600"> wiser </strong>
                            financial future.
                        </h1>

                        <p className="mt-4 text-base text-pretty text-gray-700 sm:text-lg/relaxed dark:text-gray-200">
                            Click the button below to link your wealthwise account with your bank.
                        </p>

                        <div className="mt-4 flex justify-center gap-4 sm:mt-6">
                            <a
                                className="inline-block rounded border border-indigo-600 bg-indigo-600 px-5 py-3 font-medium text-white shadow-sm transition-colors hover:bg-indigo-700"
                                href="#"
                            >
                                Get Started
                            </a>
                        </div>
                    </div>
                </div>
            </section>
            <Footer/>
        </>
            );
}