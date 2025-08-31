// src/pages/Login/LoginPage.jsx

import { useState } from 'react';
import { useAuth } from '../../contexts/AuthContext'; // 1. IMPORTAMOS EL HOOK DE AUTENTICACIÓN
import kuHubLogo from '../../assets/KüHubLogoWBG.png';
import styles from './LoginPage.module.css';
import ThemeButton from '../../components/ThemeButton/ThemeButton.jsx';

// Agregamos el nombre a cada usuario para pasarlo al contexto
const USUARIOS = {
  "admin":   { password: "1234", rol: "administracion", nombre: "Admin General" },
  "docente": { password: "abcd", rol: "docente", nombre: "Daniel Ojeda" },
  "matheus": { password: "d123", rol: "administracion", nombre: "Matheus" }
};

function LoginPage() {
  const { login } = useAuth(); // 2. OBTENEMOS LA FUNCIÓN LOGIN DEL CONTEXTO
  const [usuario, setUsuario] = useState('');
  const [contrasena, setContrasena] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    const usuarioInput = usuario.trim().toLowerCase();
    setError('');

    if (usuarioInput in USUARIOS) {
      const usuarioData = USUARIOS[usuarioInput];
      if (usuarioData.password === contrasena) {
        // 3. LLAMAMOS A LA FUNCIÓN LOGIN CON LOS DATOS DEL USUARIO
        // El contexto se encargará de guardar la sesión y redirigir.
        login({
            username: usuarioInput,
            nombre: usuarioData.nombre,
            rol: usuarioData.rol,
        });
      } else {
        setError("Contraseña incorrecta.");
      }
    } else {
      setError("Usuario no encontrado.");
    }
  };

  return (
    <>
      <div className="header">
        <h1>Inicio de Sesión</h1>
        <img className="KHlogo" src={kuHubLogo} alt="KüHub logo" />
      </div>

      <div className="principal-container">
        <ThemeButton />
        
        <form className={styles.loginForm} onSubmit={handleSubmit}>
          
          {error && <p className={styles.errorMessage}>{error}</p>}
          
          <label className="info-label">Usuario
            <input 
              type="text" 
              className="info-block" 
              autoComplete="off" 
              placeholder="Ej: docente"
              value={usuario}
              onChange={(e) => setUsuario(e.target.value)}
              required
            />
          </label>

          <label className="info-label">Contraseña
            <input 
              type="password" 
              className="info-block" 
              placeholder="••••••"
              value={contrasena}
              onChange={(e) => setContrasena(e.target.value)}
              required
            />
          </label>

          <button type="submit" className={styles.loginButton}>Ingresar</button>
        </form>
      </div>
    </>
  );
}

export default LoginPage;