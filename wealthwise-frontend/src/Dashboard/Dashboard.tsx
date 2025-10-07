import { useState, useEffect } from 'react';
import Footer from '../PageComponents/Footer';
import Header from '../PageComponents/Header';
import { getCookie } from "../Utils/Utils.tsx";
import { useNavigate } from 'react-router-dom';
import { Menu, X, Home, DollarSign, CreditCard } from "lucide-react"; // Assuming Lucide icons for sidebar

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
    const [sidebarOpen, setSidebarOpen] = useState<boolean>(false); // For mobile sidebar toggle

    const nav = useNavigate();

    const tabs = [
        { id: 'summary', label: 'Summary', icon: Home },
        { id: 'transactions', label: 'Transactions', icon: DollarSign },
        { id: 'accounts', label: 'Accounts', icon: CreditCard },
    ] as const;

    useEffect(() => {
        const fetchData = async () => {
            let token = getCookie("auth-token");
            if (!token) {
                console.error("No auth token found");
                nav("/login");
            } else {
                try {
                    setLoading(true);
                    const response = await fetch('/api/wealthwise/monthly_summary', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`,
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
            }
        };

        fetchData();
    }, [nav]);

    // Calculate total balance for summary
    const totalBalance = apiData && apiData.accountInformation && apiData.accountInformation.accounts
        ? apiData.accountInformation.accounts.reduce((sum, account) => sum + account.balances.current, 0).toFixed(2)
        : '0.00';

    return (
        <div className="flex min-h-screen bg-gray-100 dark:bg-gray-900">
            {/* Sidebar */}
            <aside
                className={`fixed inset-y-0 left-0 z-50 w-64 bg-white dark:bg-gray-800 shadow-lg transform ${
                    sidebarOpen ? 'translate-x-0' : '-translate-x-full'
                } md:translate-x-0 transition-transform duration-300 ease-in-out`}
            >
                <div className="flex items-center justify-between p-4 border-b dark:border-gray-700">
                    <h2 className="text-xl font-bold text-gray-900 dark:text-white">WealthWise</h2>
                    <button
                        className="md:hidden text-gray-500 dark:text-gray-300"
                        onClick={() => setSidebarOpen(false)}
                    >
                        <X size={24} />
                    </button>
                </div>
                <nav className="mt-4">
                    {tabs.map((tab) => (
                        <button
                            key={tab.id}
                            className={`flex items-center w-full px-4 py-3 text-sm font-medium transition-colors ${
                                activeTab === tab.id
                                    ? 'bg-indigo-100 dark:bg-indigo-900 text-indigo-600 dark:text-indigo-400'
                                    : 'text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700'
                            }`}
                            onClick={() => {
                                setActiveTab(tab.id);
                                setSidebarOpen(false); // Close sidebar on mobile after selection
                            }}
                        >
                            <tab.icon className="w-5 h-5 mr-3" />
                            {tab.label}
                        </button>
                    ))}
                </nav>
            </aside>

            {/* Main Content */}
            <div className="flex-1 flex flex-col md:ml-64">
                <Header />
                {/* Mobile Sidebar Toggle Button */}
                <button
                    className="md:hidden fixed top-4 left-4 z-50 p-2 bg-indigo-600 text-white rounded-lg"
                    onClick={() => setSidebarOpen(true)}
                >
                    <Menu size={24} />
                </button>

                <main className="flex-1 p-4 md:p-8">
                    {loading && (
                        <div className="fixed inset-0 bg-gray-900 bg-opacity-50 flex items-center justify-center z-50">
                            <div className="text-white text-xl font-semibold">Loading...</div>
                        </div>
                    )}

                    <div className="max-w-7xl mx-auto">
                        {activeTab === 'summary' && (
                            <section className="bg-gradient-to-r from-indigo-600 to-blue-500 text-white rounded-xl shadow-lg">
                                <div className="p-6 md:p-8">
                                    <h2 className="text-2xl md:text-3xl font-bold text-center">Your Financial Overview (Last 30 Days)</h2>
                                    <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                                        {[
                                            { title: 'Total Balance', value: error || !apiData ? 'Error' : `$${totalBalance}`, color: 'bg-white/20' },
                                            { title: 'Monthly Income', value: error || !apiData ? 'Error' : `$${apiData.income.toFixed(2)}`, color: 'bg-white/20' },
                                            { title: 'Monthly Expenses', value: error || !apiData ? 'Error' : `$${Math.abs(apiData.expense).toFixed(2)}`, color: 'bg-white/20' },
                                        ].map((item, index) => (
                                            <div
                                                key={index}
                                                className={`${item.color} p-6 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300`}
                                            >
                                                <h3 className="text-lg md:text-xl font-semibold">{item.title}</h3>
                                                <p className={`mt-2 text-xl md:text-2xl ${error ? 'text-red-300' : 'text-white'}`}>
                                                    {item.value}
                                                </p>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </section>
                        )}

                        {activeTab === 'transactions' && (
                            <section className="bg-white dark:bg-gray-800 rounded-xl shadow-lg">
                                <div className="p-6 md:p-8">
                                    <h2 className="text-2xl md:text-3xl font-bold text-center text-gray-900 dark:text-white">
                                        Recent Transactions (Last 30 Days)
                                    </h2>
                                    {error && (
                                        <p className="text-center mt-6 text-red-600 dark:text-red-400">Error: {error}</p>
                                    )}
                                    {apiData && apiData.transactionHistory && apiData.transactionHistory.transactions && !loading && !error && (
                                        <div className="mt-6 overflow-x-auto">
                                            <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                                                <thead>
                                                <tr>
                                                    {['Date', 'Description', 'Amount', 'Category'].map((header) => (
                                                        <th
                                                            key={header}
                                                            className="px-4 md:px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider"
                                                        >
                                                            {header}
                                                        </th>
                                                    ))}
                                                </tr>
                                                </thead>
                                                <tbody className="divide-y divide-gray-200 dark:divide-gray-700">
                                                {apiData.transactionHistory.transactions.slice(0, 5).map((transaction) => (
                                                    <tr
                                                        key={transaction.transaction_id}
                                                        className="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors"
                                                    >
                                                        <td className="px-4 md:px-6 py-4 text-gray-900 dark:text-white">
                                                            {transaction.formattedAuthorizedDate}
                                                        </td>
                                                        <td className="px-4 md:px-6 py-4 text-gray-900 dark:text-white">
                                                            <div className="flex items-center gap-3">
                                                                {transaction.counterparties.length > 0 && transaction.counterparties[0].logo_url && (
                                                                    <img
                                                                        src={transaction.counterparties[0].logo_url}
                                                                        alt={transaction.counterparties[0].name}
                                                                        className="w-8 h-8 rounded-full object-cover"
                                                                    />
                                                                )}
                                                                <span>
                                                                    {transaction.counterparties.length > 0 ? transaction.counterparties[0].name : 'Unknown'}
                                                                </span>
                                                            </div>
                                                        </td>
                                                        <td
                                                            className={`px-4 md:px-6 py-4 ${
                                                                transaction.amount < 0 ? 'text-red-500' : 'text-green-500'
                                                            }`}
                                                        >
                                                            {transaction.amount < 0
                                                                ? `-$${Math.abs(transaction.amount).toFixed(2)}`
                                                                : `+$${transaction.amount.toFixed(2)}`}
                                                        </td>
                                                        <td className="px-4 md:px-6 py-4 text-gray-600 dark:text-gray-300">
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
                            <section className="bg-indigo-600 text-white rounded-xl shadow-lg">
                                <div className="p-6 md:p-8">
                                    <h2 className="text-2xl md:text-3xl font-bold text-center">Account Overview</h2>
                                    {error && (
                                        <p className="text-center mt-6 text-red-300">Error: {error}</p>
                                    )}
                                    {apiData && apiData.accountInformation && apiData.accountInformation.accounts && !loading && !error ? (
                                        <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                                            {apiData.accountInformation.accounts.map((account) => (
                                                <div
                                                    key={account.account_id}
                                                    className="p-6 bg-white/20 rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300"
                                                >
                                                    <h3 className="text-lg md:text-xl font-semibold">{account.name || 'Unknown Account'}</h3>
                                                    <p className="mt-2 text-lg">Balance: ${account.balances.current.toFixed(2)}</p>
                                                    <p className="mt-1 text-gray-200">Account Number Ending: {account.mask || 'N/A'}</p>
                                                    <p className="mt-1 text-gray-200">Type: {account.holder_category || 'N/A'}</p>
                                                </div>
                                            ))}
                                        </div>
                                    ) : (
                                        <p className="text-center mt-6 text-gray-200">No account information available</p>
                                    )}
                                </div>
                            </section>
                        )}
                    </div>
                </main>
                <Footer />
            </div>
        </div>
    );
}
