import { Routes, Route } from 'react-router-dom';
import RegisterPage from "./RegistryComps/RegisterPage.tsx";
import LoginPage from "./LoginPage/LoginPage.tsx";
import BankLink from "./RegistryComps/BankLink.tsx";
import LandingPage from "./LandingPage/LandingPage.tsx";
import VerifyEmail from "./RegistryComps/VerifyEmail.tsx";
import Dashboard from './Dashboard/Dashboard.tsx';

export default function App() {
    return (
        <div>
            <Routes>
                <Route path="/" element={<LandingPage/>} />
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/link" element={<BankLink/>} />
                <Route path="/verify" element={<VerifyEmail/>}/>
                <Route path="/dashboard" element={<Dashboard/>}/>
                {/*<Route path="*" element={<NotFound />} />*/}
            </Routes>
        </div>
    );
}