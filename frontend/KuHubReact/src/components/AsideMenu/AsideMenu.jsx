// src/components/AsideMenu/AsideMenu.jsx

import { Link } from 'react-router-dom'; // <-- 1. IMPORTA LINK
import { useMenuContext } from '../../contexts/MenuContext';
import { useAuth } from '../../contexts/AuthContext';
import styles from './AsideMenu.module.css';

function AsideMenu() {
    const { isMenuOpen, toggleMenu } = useMenuContext();
    const { user, logout } = useAuth();

    if (!user) return null;

    const handleLinkClick = () => {
        // Cierra el menú cuando se hace clic en un enlace
        if (isMenuOpen) {
            toggleMenu();
        }
    };

    return (
        <>
            <div 
                className={`${styles.overlay} ${isMenuOpen ? styles.isOpen : ''}`}
                onClick={toggleMenu}
            ></div>
            <aside className={`${styles.aside} ${isMenuOpen ? styles.isOpen : ''}`}>
                <div className={styles.menuHeader}>
                    <h3>{user.nombre}</h3>
                    <p>Rol: {user.rol}</p>
                </div>
                <nav>
                    {/* 2. REEMPLAZA <a> POR <Link> Y AÑADE EL MANEJADOR DE CLIC */}
                    <Link to="/account" onClick={handleLinkClick}>Mi Cuenta</Link>
                    <a href="#" onClick={logout}>Cerrar Sesión</a>
                </nav>
            </aside>
        </>
    );
}

export default AsideMenu;