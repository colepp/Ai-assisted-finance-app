import Header from "../PageComponents/Header.tsx";
import Footer from "../PageComponents/Footer.tsx";
import { useEffect, useState } from "react";
import { getCookie, redirect } from "../Utils/Utils.tsx";
import { usePlaidLink, type PlaidLinkOptions } from "react-plaid-link";
import { useNavigate } from "react-router-dom";

export default function BankLink() {
    const [linkToken, setLinkToken] = useState<string | null>(null);
    const [publicToken, setPublicToken] = useState<string | null>(null);

    const navigate = useNavigate();

    useEffect(() => {
        const getlinkToken = async () => {
            const authToken = getCookie("auth-token");
            if (authToken === null) {
                console.error("User Not Valid Or Token Expired");
                return;
            }
            try {
                const response = await fetch("http://localhost:8080/plaid/api/create_link_token", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${getCookie("auth-token")}`,
                    },
                });
                const data = await response.json();
                console.log("Link Token Response:", data);
                setLinkToken(data.link_token);
                console.log(linkToken);
            } catch (error) {
                console.error("API call failed:", error);
            }
        };
        getlinkToken();
    }, []);

    const config: PlaidLinkOptions = {
        token: linkToken,
        onSuccess: (public_token, metadata) => {
            console.log("Plaid Link Success:", public_token, metadata);
            setPublicToken(public_token);
        },
        onExit: (err, metadata) => {
            console.log("Plaid Link Exit:", err, metadata);
        },
        onEvent: (eventName, metadata) => {
            console.log("Plaid Link Event:", eventName, metadata);

        },
    };

    const { open, ready } = usePlaidLink(config);

    const handleClick = () => {
        // console.log("User Starting Link");
        if (ready && linkToken) {
            open();
        } else {
            console.log("Plaid Link is not ready or linkToken is missing");
        }
    };

    useEffect(() => {
        if (publicToken) {
            const sendPublicToken = async () => {
                try {
                    const response = await fetch("http://localhost:8080/plaid/api/exchange_public_token", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${getCookie("auth-token")}` || "",
                        },
                        body: JSON.stringify({ public_token: publicToken }),
                    });
                    const data = await response.json();
                    console.log("Public Token Exchange Response:", data);
                } catch (error) {
                    console.error("Failed to exchange public token:", error);
                }
                navigate("/");
            };
            sendPublicToken();
        }
    }, [publicToken]);

    return (
        <>
            <Header />
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
                            <button
                                className="inline-block rounded border border-indigo-600 bg-indigo-600 px-5 py-3 font-medium text-white shadow-sm transition-colors hover:bg-indigo-700 disabled:opacity-50 disabled:cursor-not-allowed"
                                onClick={handleClick}
                                disabled={!ready || !linkToken}
                            >
                                Get Started
                            </button>
                        </div>
                    </div>
                </div>
            </section>
            <Footer />
        </>
    );
}