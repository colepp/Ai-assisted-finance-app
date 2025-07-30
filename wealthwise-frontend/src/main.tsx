import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import LoginPage from './LoginPage/LoginPage.tsx'
import RegisterPage from './RegisterPage/RegisterPage.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RegisterPage/>
  </StrictMode>,
)
