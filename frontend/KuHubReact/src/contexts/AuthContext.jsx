// src/contexts/AuthContext.jsx
import React, { createContext, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        // Al cargar, revisa si hay un usuario guardado en sessionStorage
        const savedUser = sessionStorage.getItem('user');
        return savedUser ? JSON.parse(savedUser) : null;
    });
    const navigate = useNavigate();

    const login = (userData) => {
        // Guardamos el usuario en el estado y en sessionStorage
        sessionStorage.setItem('user', JSON.stringify(userData));
        setUser(userData);
        
        // Redirigimos según el rol
        if (userData.rol === 'administracion') {
            navigate('/admin/hub');
        } else {
            navigate('/docente/solicitud');
        }
    };

    const logout = () => {
        // Limpiamos el estado y sessionStorage
        sessionStorage.removeItem('user');
        setUser(null);
        navigate('/login'); // Redirigimos al login
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

// Hook personalizado para usar el contexto fácilmente
export function useAuth() {
    return useContext(AuthContext);
}