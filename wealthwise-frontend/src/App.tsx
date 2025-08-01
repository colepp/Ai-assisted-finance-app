import { Routes, Route } from 'react-router-dom';
import RegisterPage from "./RegistryComps/RegisterPage.tsx";
import LoginPage from "./LoginPage/LoginPage.tsx";

export default function App() {
    return (
        <div>
            <Routes>
                {/*<Route path="/" element={<Home />} />*/}
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/login" element={<LoginPage/>}/>
                {/*<Route path="/about" element={<About />} />*/}
                {/*<Route path="*" element={<NotFound />} />*/}
            </Routes>
        </div>
    );
}