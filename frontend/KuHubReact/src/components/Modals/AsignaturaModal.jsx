import React, { useState, useEffect } from 'react';
import styles from './Modal.module.css'; // Usaremos los estilos que ya existen

const AsignaturaModal = ({ isOpen, onClose, asignatura, onSave }) => {
  // Estado interno para manejar los datos del formulario
  const [formData, setFormData] = useState({ codigo: '', nombre: '', creditos: '' });

  // Este efecto se ejecuta cuando la prop 'asignatura' cambia.
  // Sirve para llenar el formulario si estamos en modo "Editar".
  useEffect(() => {
    if (asignatura) {
      setFormData({
        codigo: asignatura.codigo || '',
        nombre: asignatura.nombre || '',
        creditos: asignatura.creditos || '',
      });
    } else {
      // Si no hay asignatura, es un modal para "Agregar", así que limpiamos el form
      setFormData({ codigo: '', nombre: '', creditos: '' });
    }
  }, [asignatura]);

  if (!isOpen) {
    return null;
  }

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData); // Llama a la función onSave que le pasamos desde la página
  };

  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  // Determinamos el título del modal dinámicamente
  const modalTitle = asignatura ? 'Editar Asignatura' : 'Agregar Nueva Asignatura';

  return (
    <div className={styles.modalOverlay} onClick={handleOverlayClick}>
      <div className={styles.modalContent}>
        <div className={styles.modalHeader}>
          <h2>{modalTitle}</h2>
          <button onClick={onClose} className={styles.closeButton}>
            &times;
          </button>
        </div>
        <div className={styles.modalBody}>
          <form onSubmit={handleSubmit} className={styles.form}>
            <div className={styles.formGroup}>
              <label className={styles.formLabel} htmlFor="codigo">Código</label>
              <input className={styles.formInput} type="text" id="codigo" name="codigo" value={formData.codigo} onChange={handleFormChange} required />
            </div>
            <div className={styles.formGroup}>
              <label className={styles.formLabel} htmlFor="nombre">Nombre de la Asignatura</label>
              <input className={styles.formInput} type="text" id="nombre" name="nombre" value={formData.nombre} onChange={handleFormChange} required />
            </div>
            <div className={styles.formGroup}>
              <label className={styles.formLabel} htmlFor="creditos">Créditos</label>
              <input className={styles.formInput} type="number" id="creditos" name="creditos" value={formData.creditos} onChange={handleFormChange} required />
            </div>
            <div className={styles.formActions}>
              <button type="button" onClick={onClose} className={styles.cancelButton}>Cancelar</button>
              <button type="submit" className={styles.submitButton}>Guardar Cambios</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AsignaturaModal;