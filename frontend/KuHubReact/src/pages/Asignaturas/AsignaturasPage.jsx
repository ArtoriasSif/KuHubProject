import React, { useState, useEffect } from 'react';

// Componentes
import AsignaturaModal from '../../components/Modals/AsignaturaModal';
import BackButton from '../../components/BackButton/BackButton';
import ThemeButton from '../../components/ThemeButton/ThemeButton'; // Opcional, si lo usas aquí

// Estilos específicos del contenido de esta página
import styles from './AsignaturasPage.module.css';

// Logo (asegúrate de que la ruta sea correcta)
import kuHubLogo from '../../assets/KüHubLogoWBG.png';

const AsignaturasPage = () => {
  const [asignaturas, setAsignaturas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedAsignatura, setSelectedAsignatura] = useState(null);

  // Carga de datos inicial (sin cambios)
  useEffect(() => {
    setTimeout(() => {
      setAsignaturas([
        { id: 1, codigo: 'MAT101', nombre: 'Matemáticas Básicas', creditos: 4 },
        { id: 2, codigo: 'FIS102', nombre: 'Física General', creditos: 5 },
        { id: 3, codigo: 'QUI103', nombre: 'Química General', creditos: 4 },
        { id: 4, codigo: 'PROG104', nombre: 'Programación I', creditos: 3 },
      ]);
      setLoading(false);
    }, 1500);
  }, []);

  // Manejadores del Modal y CRUD (sin cambios)
  const handleOpenModal = (asignatura = null) => {
    setSelectedAsignatura(asignatura);
    setIsModalOpen(true);
  };
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setSelectedAsignatura(null);
  };
  const handleDelete = (id) => {
    setAsignaturas(asignaturas.filter(a => a.id !== id));
  };
  const handleSaveAsignatura = (formDataDelModal) => {
    if (selectedAsignatura) {
      setAsignaturas(asignaturas.map(a => 
        a.id === selectedAsignatura.id ? { ...a, ...formDataDelModal } : a
      ));
    } else {
      const nuevaAsignatura = { id: Date.now(), ...formDataDelModal };
      setAsignaturas([...asignaturas, nuevaAsignatura]);
    }
    handleCloseModal();
  };

  return (
    // Usamos un Fragment para agrupar todo
    <>
      {/* 1. Encabezado global, idéntico a SolicitudPage */}
      <div className="header">
        <h1>Gestión de Asignaturas</h1>
        <img className="KHlogo" src={kuHubLogo} alt="Logo de KüHub" />
      </div>
      
      {/* Botones flotantes (si los tienes así en otras páginas) */}
      <ThemeButton />
      <BackButton />

      {/* 2. Contenedor principal que centra el contenido */}

        <div className={`principal-container ${styles.fullWidthOverride}`}>


        {/* La tarjeta de contenido usa los estilos del módulo para mantenerse encapsulada */}
        <div className={styles.contentCard}>
          {loading ? (
            <div className={styles.loadingContainer}>
              <div className={styles.spinner}></div>
              <p>Cargando asignaturas...</p>
            </div>
          ) : (
            <>
              <div className={styles.actionBar}>
                <button onClick={() => handleOpenModal()} className={styles.addButton}>
                  Agregar Asignatura
                </button>
              </div>

              <div className={styles.tableContainer}>
                <table className={styles.table}>
                  <thead>
                    <tr>
                      <th>Código</th>
                      <th>Nombre</th>
                      <th>Créditos</th>
                      <th className={styles.actionsHeader}>Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    {asignaturas.map((asignatura) => (
                      <tr key={asignatura.id}>
                        <td data-label="Código">{asignatura.codigo}</td>
                        <td data-label="Nombre">{asignatura.nombre}</td>
                        <td data-label="Créditos">{asignatura.creditos}</td>
                        <td data-label="Acciones" className={styles.actionsCell}>
                          <button onClick={() => handleOpenModal(asignatura)} className={styles.editButton}>Editar</button>
                          <button onClick={() => handleDelete(asignatura.id)} className={styles.deleteButton}>Eliminar</button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </>
          )}
        </div>
      </div>

      {/* El modal se mantiene igual */}
      <AsignaturaModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        asignatura={selectedAsignatura}
        onSave={handleSaveAsignatura}
      />
    </>
  );
};

export default AsignaturasPage;