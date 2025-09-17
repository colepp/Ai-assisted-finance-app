
import ReactDOM from 'react-dom/client'; // Note the /client import
import { BrowserRouter } from 'react-router-dom';
import App from './App';

const container = document.getElementById('root');
if (container) {
    const root = ReactDOM.createRoot(container);
    root.render(
        <BrowserRouter>
            <App />
        </BrowserRouter>
    );
} else {
    throw new Error("Root container not found");
}