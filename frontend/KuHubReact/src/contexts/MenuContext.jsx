// src/contexts/MenuContext.jsx
import React, { createContext, useState, useContext } from 'react';

// 1. Creamos el Contexto
const MenuContext = createContext();

// 2. Creamos un "Proveedor" que envolverá nuestra aplicación
export function MenuProvider({ children }) {
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const toggleMenu = () => {
        setIsMenuOpen(prev => !prev);
    };

    return (
        <MenuContext.Provider value={{ isMenuOpen, toggleMenu }}>
            {children}
        </MenuContext.Provider>
    );
}

// 3. Creamos un hook personalizado para usar el contexto fácilmente
export function useMenuContext() {
    return useContext(MenuContext);
}