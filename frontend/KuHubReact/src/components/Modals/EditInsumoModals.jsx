// src/components/Modals/EditInsumoModal.jsx
import { useState, useEffect } from 'react';
import styles from './Modal.module.css'; // Crearemos un CSS para todos los modales

function EditInsumoModal({ insumo, onClose, onSave }) {
    const [nombre, setNombre] = useState('');
    const [ubicacion, setUbicacion] = useState('');

    // Cuando el insumo a editar cambia, llenamos el formulario
    useEffect(() => {
        if (insumo) {
            setNombre(insumo.nombre);
            setUbicacion(Array.isArray(insumo.ubicacion) ? insumo.ubicacion.join(', ') : insumo.ubicacion);
        }
    }, [insumo]);

    const handleSave = () => {
        const ubicacionArray = ubicacion.split(',').map(u => u.trim()).filter(Boolean);
        onSave({ ...insumo, nombre, ubicacion: ubicacionArray });
    };

    if (!insumo) return null; // No renderizar nada si no hay un insumo para editar

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    <h2>Editar Insumo</h2>
                    <span className={styles.closeButton} onClick={onClose}>&times;</span>
                </div>
                <div className="form-section">
                    <label className="info-label">Nombre
                        <input 
                            type="text" 
                            className="info-block" 
                            value={nombre} 
                            onChange={(e) => setNombre(e.target.value)}
                        />
                    </label>
                    <label className="info-label">Ubicación (ej: A1, B2)
                        <input 
                            type="text" 
                            className="info-block" 
                            value={ubicacion} 
                            onChange={(e) => setUbicacion(e.target.value)}
                        />
                    </label>
                    <button onClick={handleSave} className="info-block">Guardar cambios</button>
                </div>
            </div>
        </div>
    );
}

export default EditInsumoModal;