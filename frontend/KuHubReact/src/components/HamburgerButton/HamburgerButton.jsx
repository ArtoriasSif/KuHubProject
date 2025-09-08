// src/components/HamburgerButton/HamburgerButton.jsx
import { useMenuContext } from '../../contexts/MenuContext';
import styles from './HamburgerButton.module.css';

function HamburgerButton() {
    const { isMenuOpen, toggleMenu } = useMenuContext();

    return (
        <button 
            className={`${styles.hamburger} ${isMenuOpen ? styles.isActive : ''}`} 
            onClick={toggleMenu}
            aria-label="Toggle menu"
        >
            <div className={styles.bar}></div>
        </button>
    );
}

export default HamburgerButton;