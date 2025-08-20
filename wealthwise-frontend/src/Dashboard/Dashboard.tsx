
import React from 'react';
import Footer from '../PageComponents/Footer';
import Header from '../PageComponents/Header';

import { useState } from 'react';

export default function Dashboard() {
  const [activeTab, setActiveTab] = useState('summary');

  const tabs = [
    { id: 'summary', label: 'Summary' },
    { id: 'transactions', label: 'Transactions' },
    { id: 'accounts', label: 'Accounts' },
  ];

  return (
    <>
    <Header></Header>
    <main className="flex-grow bg-gray-100 dark:bg-gray-900">
      {/* Tabs */}
      <div className="bg-white dark:bg-gray-800 shadow">
        <div className="mx-auto max-w-screen-xl px-4 sm:px-6 lg:px-8">
          <nav className="flex border-b border-gray-200 dark:border-gray-700">
            {tabs.map((tab) => (
              <button
                key={tab.id}
                className={`px-4 py-2 text-sm font-medium ${
                  activeTab === tab.id
                    ? 'border-b-2 border-indigo-600 text-indigo-600 dark:text-indigo-400'
                    : 'text-gray-500 dark:text-gray-300 hover:text-indigo-600 dark:hover:text-indigo-400'
                }`}
                onClick={() => setActiveTab(tab.id)}
              >
                {tab.label}
              </button>
            ))}
          </nav>
        </div>
      </div>

      {/* Tab Content */}
      <div className="mx-auto max-w-screen-xl px-4 py-12 sm:px-6 lg:px-8">
        {activeTab === 'summary' && (
          <section className="bg-gradient-to-r from-indigo-600 to-blue-500 text-white rounded-lg">
            <div className="px-4 py-12 sm:px-6 lg:px-8">
              <h2 className="text-3xl font-bold text-center">Your Financial Overview</h2>
              <div className="mt-8 grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                  <h3 className="text-xl font-semibold">Total Balance</h3>
                  <p className="mt-2 text-2xl">$12,345.67</p>
                </div>
                <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                  <h3 className="text-xl font-semibold">Monthly Income</h3>
                  <p className="mt-2 text-2xl">$4,500.00</p>
                </div>
                <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                  <h3 className="text-xl font-semibold">Monthly Expenses</h3>
                  <p className="mt-2 text-2xl">$3,200.00</p>
                </div>
              </div>
            </div>
          </section>
        )}

        {activeTab === 'transactions' && (
          <section className="bg-white dark:bg-gray-800 rounded-lg">
            <div className="px-4 py-12 sm:px-6 lg:px-8">
              <h2 className="text-3xl font-bold text-center text-gray-900 dark:text-white">
                Recent Transactions
              </h2>
              <div className="mt-8 overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                  <thead>
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
                        Date
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
                        Description
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
                        Amount
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
                        Category
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 dark:divide-gray-700">
                    <tr>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Aug 15, 2025</td>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Grocery Store</td>
                      <td className="px-6 py-4 text-red-500">-$85.32</td>
                      <td className="px-6 py-4 text-gray-600 dark:text-gray-300">Food</td>
                    </tr>
                    <tr>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Aug 14, 2025</td>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Salary Deposit</td>
                      <td className="px-6 py-4 text-green-500">+$2,500.00</td>
                      <td className="px-6 py-4 text-gray-600 dark:text-gray-300">Income</td>
                    </tr>
                    <tr>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Aug 13, 2025</td>
                      <td className="px-6 py-4 text-gray-900 dark:text-white">Electric Bill</td>
                      <td className="px-6 py-4 text-red-500">-$120.00</td>
                      <td className="px-6 py-4 text-gray-600 dark:text-gray-300">Utilities</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </section>
        )}

        {activeTab === 'accounts' && (
          <section className="bg-indigo-600 text-white rounded-lg">
            <div className="px-4 py-12 sm:px-6 lg:px-8">
              <h2 className="text-3xl font-bold text-center">Account Overview</h2>
              <div className="mt-8 grid grid-cols-1 gap-6 sm:grid-cols-2">
                <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                  <h3 className="text-xl font-semibold">Savings Account</h3>
                  <p className="mt-2 text-lg">Balance: $8,000.00</p>
                  <p className="mt-1 text-gray-200">Interest Rate: 2.5% APR</p>
                </div>
                <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                  <h3 className="text-xl font-semibold">Checking Account</h3>
                  <p className="mt-2 text-lg">Balance: $4,345.67</p>
                  <p className="mt-1 text-gray-200">Last Transaction: Aug 15, 2025</p>
                </div>
              </div>
            </div>
          </section>
        )}
      </div>
    </main>
    <Footer></Footer>
    </>
  );
};
