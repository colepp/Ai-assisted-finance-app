
import React from 'react';
import Header from '../PageComponents/Header.tsx'// Assuming Header.tsx is in the same directory
import Footer from '../PageComponents/Footer'; // Assuming Footer.tsx is in the same directory

export default function LandingPage(){
  return (
    <div className="flex flex-col min-h-screen">
      <Header />

      <main className="flex-grow">
        {/* Hero Section */}
        <section className="bg-gradient-to-r from-indigo-600 to-blue-500 text-white">
          <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8">
            <div className="text-center">
              <h1 className="text-4xl font-extrabold sm:text-5xl md:text-6xl">
                Welcome to Our Platform
              </h1>
              <p className="mt-4 max-w-md mx-auto text-lg sm:text-xl">
                Discover innovative solutions and elevate your business with our cutting-edge services.
              </p>
              <div className="mt-8 flex justify-center gap-4">
                <a
                  href="/register"
                  className="inline-block rounded-md bg-white px-6 py-3 text-indigo-600 font-medium hover:bg-gray-100 transition"
                >
                  Get Started
                </a>
                <a
                  href="/learn-more"
                  className="inline-block rounded-md border border-white px-6 py-3 text-white font-medium hover:bg-white hover:text-indigo-600 transition"
                >
                  Learn More
                </a>
              </div>
            </div>
          </div>
        </section>

        {/* Features Section */}
        <section className="bg-white dark:bg-gray-900">
          <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8">
            <h2 className="text-3xl font-bold text-center text-gray-900 dark:text-white">
              Why Choose Us?
            </h2>
            <div className="mt-12 grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
              <div className="p-6 bg-gray-100 dark:bg-gray-800 rounded-lg shadow-sm">
                <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                  Expert Guidance
                </h3>
                <p className="mt-4 text-gray-600 dark:text-gray-300">
                  Our team of professionals provides tailored 1-on-1 coaching to drive your success.
                </p>
              </div>
              <div className="p-6 bg-gray-100 dark:bg-gray-800 rounded-lg shadow-sm">
                <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                  Comprehensive Reviews
                </h3>
                <p className="mt-4 text-gray-600 dark:text-gray-300">
                  In-depth company and account reviews to optimize performance and efficiency.
                </p>
              </div>
              <div className="p-6 bg-gray-100 dark:bg-gray-800 rounded-lg shadow-sm">
                <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
                  SEO Excellence
                </h3>
                <p className="mt-4 text-gray-600 dark:text-gray-300">
                  Boost your online presence with our advanced SEO optimization strategies.
                </p>
              </div>
            </div>
          </div>
        </section>

        {/* Call to Action Section */}
        <section className="bg-indigo-600 text-white">
          <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8">
            <div className="text-center">
              <h2 className="text-3xl font-bold">
                Ready to Transform Your Business?
              </h2>
              <p className="mt-4 max-w-md mx-auto text-lg">
                Join thousands of satisfied clients and take the first step towards success.
              </p>
              <a
                href="/signup"
                className="mt-8 inline-block rounded-md bg-white px-6 py-3 text-indigo-600 font-medium hover:bg-gray-100 transition"
              >
                Sign Up Now
              </a>
            </div>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
};


