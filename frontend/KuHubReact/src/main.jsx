// src/main.jsx

import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App.jsx';

// --- ORDEN CORRECTO DE ESTILOS ---
// 1. Primero importa Bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
// 2. Después importa tus estilos para que puedan sobrescribir a Bootstrap
import './assets/styles/main.css';
// ------------------------------------

import { MenuProvider } from './contexts/MenuContext';
import { AuthProvider } from './contexts/AuthContext';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <MenuProvider>
          <App />
        </MenuProvider>
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>,
);