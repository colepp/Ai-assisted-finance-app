
import React, { useState, useEffect } from 'react';
import Footer from '../PageComponents/Footer';
import Header from '../PageComponents/Header';
import { getCookie } from "../Utils/Utils.tsx";

interface Transaction {
  pending: boolean;
  amount: number;
  formattedAuthorizedDate: string;
  transaction_id: string;
  authorized_date: [number, number, number];
  payment_channel: string;
  counterparties: Array<{
    name: string;
    type: string;
    logo_url: string | null;
    confidence_level: string;
  }>;
  personal_finance_category: {
    primary: string;
    detailed: string;
    confidence_level: string;
  };
  account_owner: string | null;
  iso_currency_code: string;
}

interface Account {
  account_id: string;
  balances: {
    available: number | null;
    current: number;
    limit: number | null;
    iso_currency_code: string;
  };
  mask: string;
  name: string;
  official_name: string;
  holder_category: string;
}

interface ApiResponse {
  transactionHistory: {
    transactions: Transaction[];
    request_id: string;
    total_transactions: number;
  };
  accountInformation: {
    accounts: Account[];
  } | null;
  income: number;
  expense: number;
}

export default function Dashboard() {
  const [activeTab, setActiveTab] = useState<'summary' | 'transactions' | 'accounts'>('summary');
  const [apiData, setApiData] = useState<ApiResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const tabs = [
    { id: 'summary', label: 'Summary' },
    { id: 'transactions', label: 'Transactions' },
    { id: 'accounts', label: 'Accounts' },
  ] as const;

  useEffect(() => {
    const fetchData = async () => {
      console.log("fetching data");
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/plaid/api/monthly_summary', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getCookie("auth-token")}`,
          },
        });
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const data: ApiResponse = await response.json();
        setApiData(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'An error occurred');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // Calculate total balance for summary
  const totalBalance = apiData && apiData.accountInformation && apiData.accountInformation.accounts
    ? apiData.accountInformation.accounts.reduce((sum, account) => sum + account.balances.current, 0).toFixed(2)
    : '0.00';

  return (
    <>
      <Header />
      <main className="flex-grow bg-gray-100 dark:bg-gray-900 relative">
        {loading && (
          <div className="absolute inset-0 bg-gray-900 bg-opacity-50 flex items-center justify-center z-50">
            <div className="text-white text-xl font-semibold">
              Loading...
            </div>
          </div>
        )}
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

        <div className="mx-auto max-w-screen-xl px-4 py-12 sm:px-6 lg:px-8">
          {activeTab === 'summary' && (
            <section className="bg-gradient-to-r from-indigo-600 to-blue-500 text-white rounded-lg">
              <div className="px-4 py-12 sm:px-6 lg:px-8">
                <h2 className="text-3xl font-bold text-center">Your Financial Overview (Last 30 Days)</h2>
                <div className="mt-8 grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                  <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                    <h3 className="text-xl font-semibold">Total Balance</h3>
                    {error || !apiData ? (
                      <p className="mt-2 text-2xl text-red-300">{error ? 'Error' : '0.00'}</p>
                    ) : (
                      <p className="mt-2 text-2xl">${totalBalance}</p>
                    )}
                  </div>
                  <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                    <h3 className="text-xl font-semibold">Monthly Income</h3>
                    {error || !apiData ? (
                      <p className="mt-2 text-2xl text-red-300">{error ? 'Error' : '0.00'}</p>
                    ) : (
                      <p className="mt-2 text-2xl">${apiData.income.toFixed(2)}</p>
                    )}
                  </div>
                  <div className="p-6 bg-white/20 rounded-lg shadow-sm">
                    <h3 className="text-xl font-semibold">Monthly Expenses</h3>
                    {error || !apiData ? (
                      <p className="mt-2 text-2xl text-red-300">{error ? 'Error' : '0.00'}</p>
                    ) : (
                      <p className="mt-2 text-2xl">${Math.abs(apiData.expense).toFixed(2)}</p>
                    )}
                  </div>
                </div>
              </div>
            </section>
          )}

          {activeTab === 'transactions' && (
            <section className="bg-white dark:bg-gray-800 rounded-lg">
              <div className="px-4 py-12 sm:px-6 lg:px-8">
                <h2 className="text-3xl font-bold text-center text-gray-900 dark:text-white">
                  Recent Transactions (Last 30 Days)
                </h2>
                {error && (
                  <p className="text-center mt-8 text-red-600 dark:text-red-400">Error: {error}</p>
                )}
                {apiData && apiData.transactionHistory && apiData.transactionHistory.transactions && !loading && !error && (
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
                        {apiData.transactionHistory.transactions.slice(0, 5).map((transaction) => (
                          <tr key={transaction.transaction_id}>
                            <td className="px-6 py-4 text-gray-900 dark:text-white">
                              {transaction.formattedAuthorizedDate}
                            </td>
                            <td className="px-6 py-4 text-gray-900 dark:text-white">
                              {transaction.counterparties.length > 0
                                ? transaction.counterparties[0].name
                                : 'Unknown'}
                            </td>
                            <td className={`px-6 py-4 ${transaction.amount < 0 ? 'text-red-500' : 'text-green-500'}`}>
                              {transaction.amount < 0
                                ? `-$${Math.abs(transaction.amount).toFixed(2)}`
                                : `+$${transaction.amount.toFixed(2)}`}
                            </td>
                            <td className="px-6 py-4 text-gray-600 dark:text-gray-300">
                              {transaction.personal_finance_category.primary || 'Unknown'}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            </section>
          )}

          {activeTab === 'accounts' && (
            <section className="bg-indigo-600 text-white rounded-lg">
              <div className="px-4 py-12 sm:px-6 lg:px-8">
                <h2 className="text-3xl font-bold text-center">Account Overview</h2>
                {error && (
                  <p className="text-center mt-8 text-red-300">Error: {error}</p>
                )}
                {apiData && apiData.accountInformation && apiData.accountInformation.accounts && !loading && !error ? (
                  <div className="mt-8 grid grid-cols-1 gap-6 sm:grid-cols-2">
                    {apiData.accountInformation.accounts.map((account) => (
                      <div key={account.account_id} className="p-6 bg-white/20 rounded-lg shadow-sm">
                        <h3 className="text-xl font-semibold">{account.name || 'Unknown Account'}</h3>
                        <p className="mt-2 text-lg">Balance: ${account.balances.current.toFixed(2)}</p>
                        <p className="mt-1 text-gray-200">Account Number Ending: {account.mask || 'N/A'}</p>
                        <p className="mt-1 text-gray-200">Type: {account.holder_category || 'N/A'}</p>
                      </div>
                    ))}
                  </div>
                ) : (
                  <p className="text-center mt-8 text-gray-200">No account information available</p>
                )}
              </div>
            </section>
          )}
        </div>
      </main>
      <Footer />
    </>
  );
};