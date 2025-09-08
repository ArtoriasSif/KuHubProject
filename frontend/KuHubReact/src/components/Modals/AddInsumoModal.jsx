// src/components/Modals/AddInsumoModal.jsx

import { useState } from 'react';
import * as XLSX from 'xlsx'; // Importamos la librería de Excel
import styles from './Modal.module.css';

function AddInsumoModal({ inventario, onClose, onSaveManual, onSaveExisting, onFileUpload }) {
    const [activeTab, setActiveTab] = useState('manual'); // 'manual', 'existing', 'excel'

    // Estado para la pestaña "Manual"
    const [manualNombre, setManualNombre] = useState('');
    const [manualUnidad, setManualUnidad] = useState('Unidad');
    const [manualUbicacion, setManualUbicacion] = useState('');
    const [manualCantidad, setManualCantidad] = useState(1);

    // Estado para la pestaña "Existente"
    const [insumoExistenteId, setInsumoExistenteId] = useState('');
    const [cantidadExistente, setCantidadExistente] = useState(1);
    
    // Estado para la pestaña "Excel"
    const [excelFile, setExcelFile] = useState(null);

    const handleSaveManual = () => {
        if (!manualNombre || !manualUnidad || !manualCantidad) return alert('Completa los campos.');
        onSaveManual({
            nombre: manualNombre,
            unidad_medida: manualUnidad,
            ubicacion: manualUbicacion.split(',').map(u => u.trim()).filter(Boolean),
            cantidad_total: parseFloat(manualCantidad),
        });
        onClose();
    };
    
    const handleSaveExisting = () => {
        if (!insumoExistenteId || !cantidadExistente) return alert('Selecciona un insumo y una cantidad.');
        onSaveExisting(parseInt(insumoExistenteId, 10), parseFloat(cantidadExistente));
        onClose();
    };

    const handleFileChange = (e) => {
        setExcelFile(e.target.files[0]);
    };

    const handleFileUpload = () => {
        if (!excelFile) return alert('Por favor, selecciona un archivo.');
        const reader = new FileReader();
        reader.readAsArrayBuffer(excelFile);
        reader.onload = (e) => {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, { type: 'array' });
            const sheetName = workbook.SheetNames[0];
            const worksheet = workbook.Sheets[sheetName];
            const json = XLSX.utils.sheet_to_json(worksheet);
            onFileUpload(json); // Enviamos los datos parseados al padre
            onClose();
        };
    };

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    <h2>Agregar Insumo</h2>
                    <span className={styles.closeButton} onClick={onClose}>&times;</span>
                </div>

                {/* Pestañas para seleccionar el método de carga */}
                <div className={styles.tabContainer}>
                    <button onClick={() => setActiveTab('manual')} className={activeTab === 'manual' ? styles.activeTab : ''}>Manual</button>
                    <button onClick={() => setActiveTab('existing')} className={activeTab === 'existing' ? styles.activeTab : ''}>A Existente</button>
                    <button onClick={() => setActiveTab('excel')} className={activeTab === 'excel' ? styles.activeTab : ''}>Desde Excel</button>
                </div>

                {/* Contenido de la pestaña "Manual" */}
                {activeTab === 'manual' && (
                    <div className="form-section">
                        <h3>Agregar insumo manualmente</h3>
                        <label className="info-label">Nombre<input value={manualNombre} onChange={e => setManualNombre(e.target.value)} type="text" className="info-block" /></label>
                        <label className="info-label">Unidad de Medida
                            <select value={manualUnidad} onChange={e => setManualUnidad(e.target.value)} className="info-block">
                                <option>Unidad</option><option>KG</option><option>Litro</option>
                            </select>
                        </label>
                        <label className="info-label">Ubicación<input value={manualUbicacion} onChange={e => setManualUbicacion(e.target.value)} type="text" className="info-block" /></label>
                        <label className="info-label">Cantidad<input value={manualCantidad} onChange={e => setManualCantidad(e.target.value)} type="number" min="1" className="info-block" /></label>
                        <button onClick={handleSaveManual} className="info-block">Guardar nuevo insumo</button>
                    </div>
                )}

                {/* Contenido de la pestaña "A Existente" */}
                {activeTab === 'existing' && (
                    <div className="form-section">
                        <h3>Agregar a insumo existente</h3>
                        <label className="info-label">Buscar producto
                            <select value={insumoExistenteId} onChange={e => setInsumoExistenteId(e.target.value)} className="info-block">
                                <option value="">-- Selecciona un insumo --</option>
                                {inventario.map(item => <option key={item.codigo} value={item.codigo}>{item.nombre}</option>)}
                            </select>
                        </label>
                        <label className="info-label">Cantidad a agregar<input value={cantidadExistente} onChange={e => setCantidadExistente(e.target.value)} type="number" min="1" className="info-block" /></label>
                        <button onClick={handleSaveExisting} className="info-block">Agregar</button>
                    </div>
                )}
                
                {/* Contenido de la pestaña "Desde Excel" */}
                {activeTab === 'excel' && (
                     <div className="form-section">
                        <h3>Agregar inventario desde Excel</h3>
                        <label className="info-label">Cargar archivo de inventario (.xlsx, .xls)
                            <input type="file" onChange={handleFileChange} className="info-block" accept=".xlsx, .xls, .csv" />
                        </label>
                        <button onClick={handleFileUpload} className="info-block">Agregar desde Archivo</button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default AddInsumoModal;