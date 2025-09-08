import React, { useState, useEffect, useRef } from 'react'; // 1. Añadimos useEffect y useRef
import { useAuth } from '../../contexts/AuthContext';
import kuHubLogo from '../../assets/KüHubLogoWBG.png';
import ThemeButton from '../../components/ThemeButton/ThemeButton.jsx';
import BackButton from '../../components/BackButton/BackButton.jsx';
import styles from './AccountPage.module.css';

function AccountPage() {
    const { user } = useAuth();

    // 2. AÑADIMOS EL ESTADO Y LA REFERENCIA PARA LA IMAGEN
    const [profilePic, setProfilePic] = useState('https://via.placeholder.com/150');
    const fileInputRef = useRef(null);

    // 3. AÑADIMOS useEffect PARA CARGAR LA IMAGEN GUARDADA DESDE localStorage
    useEffect(() => {
        // Nos aseguramos de que 'user' exista antes de buscar la foto
        if (user && user.username) {
            const savedPic = localStorage.getItem(`profilePic_${user.username}`);
            if (savedPic) {
                setProfilePic(savedPic);
            }
        }
    }, [user]); // Este efecto se ejecuta cuando los datos del usuario están disponibles

    // 4. AÑADIMOS LA FUNCIÓN PARA MANEJAR LA SELECCIÓN Y GUARDADO DE LA IMAGEN
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64String = reader.result;
                setProfilePic(base64String); // Actualiza la imagen en la pantalla
                // Guardamos la imagen asociada al nombre de usuario para que sea única
                localStorage.setItem(`profilePic_${user.username}`, base64String);
            };
            reader.readAsDataURL(file);
        }
    };
    
    // (El resto de tu lógica de estado para el formulario de contraseña no cambia)
    const [passwordData, setPasswordData] = useState({
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
    });

    const handlePasswordFormChange = (e) => {
        const { name, value } = e.target;
        setPasswordData(prev => ({...prev, [name]: value}));
    };

    const handlePasswordUpdate = (e) => {
        e.preventDefault();
        if (passwordData.newPassword !== passwordData.confirmPassword) {
            return alert("Las contraseñas nuevas no coinciden.");
        }
        console.log("Contraseña actualizada (simulado):", passwordData);
        alert("Contraseña actualizada con éxito.");
        // Limpiar formulario
        setPasswordData({ currentPassword: '', newPassword: '', confirmPassword: '' });
    };

    if (!user) {
        return <div>Debes iniciar sesión para ver esta página.</div>;
    }

    return (
        <>
            <div className="header">
                <h1>Mi Cuenta</h1>
                <img className="KHlogo" src={kuHubLogo} alt="KüHub logo" />
            </div>
            <ThemeButton />
            <BackButton />

            <div className={`principal-container ${styles.accountContainer}`}>
                
                {/* --- 5. SECCIÓN DE FOTO DE PERFIL (INTEGRADA) --- */}
                <div className={styles.profilePictureSection}>
                    <img src={profilePic} alt="Foto de perfil" className={styles.profilePicture} />
                    <input
                        type="file"
                        accept="image/*"
                        style={{ display: 'none' }}
                        ref={fileInputRef}
                        onChange={handleImageChange}
                    />
                    <button className="login-button" onClick={() => fileInputRef.current.click()}>
                        Cambiar Foto
                    </button>
                </div>

                <div className="section-title">Información del Perfil</div>
                <div className={styles.profileDetails}>
                    <div className={styles.infoRow}>
                        <span className={styles.infoLabel}>Usuario:</span>
                        <span>{user.username}</span>
                    </div>
                    <div className={styles.infoRow}>
                        <span className={styles.infoLabel}>Nombre:</span>
                        <span>{user.nombre}</span>
                    </div>
                    <div className={styles.infoRow}>
                        <span className={styles.infoLabel}>Rol:</span>
                        <span>{user.rol}</span>
                    </div>
                </div>

                <div className={styles.actionButtons}>
                    <button type="button" className="info-block">Editar Datos</button>
                </div>
                
                <div className="section-title">Cambiar Contraseña</div>
                
                <form onSubmit={handlePasswordUpdate} className={styles.passwordForm}>
                    <label className="info-label">Contraseña Actual
                        <input 
                            type="password" 
                            name="currentPassword"
                            className="info-block" 
                            autoComplete="current-password"
                            value={passwordData.currentPassword}
                            onChange={handlePasswordFormChange}
                            required 
                        />
                    </label>
                    <label className="info-label">Nueva Contraseña
                        <input 
                            type="password" 
                            name="newPassword"
                            className="info-block" 
                            autoComplete="new-password"
                            value={passwordData.newPassword}
                            onChange={handlePasswordFormChange}
                            required 
                        />
                    </label>
                    <label className="info-label">Confirmar Nueva Contraseña
                        <input 
                            type="password" 
                            name="confirmPassword"
                            className="info-block" 
                            autoComplete="new-password"
                            value={passwordData.confirmPassword}
                            onChange={handlePasswordFormChange}
                            required 
                        />
                    </label>
                    
                    <button type="submit" className="login-button">Actualizar Contraseña</button>
                </form>
            </div>
        </>
    );
}

export default AccountPage;