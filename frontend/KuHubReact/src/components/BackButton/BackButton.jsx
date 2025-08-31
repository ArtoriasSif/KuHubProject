// src/components/BackButton/BackButton.jsx

import { useNavigate } from 'react-router-dom';

function BackButton() {
  const navigate = useNavigate();

  // La función navigate(-1) es como hacer clic en el botón "atrás" del navegador
  const handleGoBack = () => {
    navigate(-1);
  };

  return (
    // La clase "boton-volver-container" ya existe en tu main.css
    <div onClick={handleGoBack} className="boton-volver-container" title="Volver">
      ‹
    </div>
  );
}

export default BackButton;