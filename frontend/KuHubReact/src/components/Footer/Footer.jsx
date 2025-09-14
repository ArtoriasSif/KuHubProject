import React from 'react';
import styles from './Footer.module.css';

//Creamos la funciom Footer
function Footer() {
    return (
        <footer className={styles.footerContainer}>
            <p>&copy; {new Date().getFullYear()} KuHub System | Version 0.1 </p>
            <p>Proyecto de Titulo - Analista Programador / Ingenieria Informatica</p>
            </footer>
    );
}

export default Footer;