import { useState, useEffect } from 'react';

function ThemeButton() {
  // 1. useState para guardar el tema actual.
  // Leemos el tema guardado en localStorage para recordar la última preferencia.
  const [theme, setTheme] = useState(() => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme || 'light'; // Si no hay nada guardado, el default es 'light'
  });

  // 2. useEffect para aplicar el cambio al DOM y guardarlo.
  // Este código se ejecuta cada vez que el estado 'theme' cambia.
  useEffect(() => {
    document.body.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
  }, [theme]); // El array [theme] le dice a React que solo ejecute esto si 'theme' cambia.

  // 3. Función para cambiar el tema al hacer clic.
  const handleThemeToggle = () => {
    setTheme(prevTheme => (prevTheme === 'light' ? 'dark' : 'light'));
  };

  // 4. El botón ahora tiene un onClick y su ícono cambia según el tema.
  return (
    <button onClick={handleThemeToggle} className="mode-toggle-button">
      {theme === 'light' ? '🌙' : '☀️'}
    </button>
  );
}

export default ThemeButton;