import { useState } from 'react';
import * as XLSX from 'xlsx';
import styles from './Modal.module.css';

function AddInsumoModal({ inventario, onClose, onSave }) {
    const [activeTab, setActiveTab] = useState('manual');

    // Estado para la pestaña "Manual"
    const [manualNombre, setManualNombre] = useState('');
    const [manualUnidad, setManualUnidad] = useState('Unidad');
    const [manualCategoria, setManualCategoria] = useState('');
    
    // Estado para la pestaña "Existente"
    const [insumoExistenteId, setInsumoExistenteId] = useState('');
    const [cantidadExistente, setCantidadExistente] = useState(1);
    
    // Estado para la pestaña "Excel"
    const [excelFile, setExcelFile] = useState(null);

    const handleSaveManual = () => {
        if (!manualNombre || !manualUnidad || !manualCategoria) return alert('Completa todos los campos.');
        // La función onSave se pasa desde el padre. Nos aseguramos de que tenga 'manual'.
        if (onSave && onSave.manual) {
            onSave.manual({
                nombreProducto: manualNombre, // <-- ESTA ES LA PROPIEDAD CORRECTA
                unidadMedida: manualUnidad,
                categoria: manualCategoria,
                stock: 0
            });
        }
        onClose();
    };
    
    const handleSaveExisting = () => {
        if (!insumoExistenteId || !cantidadExistente) return alert('Selecciona un insumo y una cantidad.');
        // Aquí necesitarás una función 'existing' en el onSave que venga del padre
        if (onSave && onSave.existing) {
           onSave.existing(parseInt(insumoExistenteId, 10), parseFloat(cantidadExistente));
        }
        onClose();
    };

    const handleFileChange = (e) => setExcelFile(e.target.files[0]);

    const handleFileUpload = () => {
        if (!excelFile) return alert('Por favor, selecciona un archivo.');
        const reader = new FileReader();
        reader.readAsArrayBuffer(excelFile);
        reader.onload = (e) => {
            try {
                const data = new Uint8Array(e.target.result);
                const workbook = XLSX.read(data, { type: 'array' });
                if (onSave && onSave.excel) {
                    onSave.excel(workbook);
                }
                onClose();
            } catch (error) {
                alert("Error al leer el archivo Excel.");
                console.error("Error procesando Excel:", error);
            }
        };
    };

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={styles.modalHeader}>
                    <h2>Agregar Insumo</h2>
                    <span className={styles.closeButton} onClick={onClose}>&times;</span>
                </div>

                <div className={styles.tabContainer}>
                    <button onClick={() => setActiveTab('manual')} className={activeTab === 'manual' ? styles.activeTab : ''}>Manual</button>
                    <button onClick={() => setActiveTab('existing')} className={activeTab === 'existing' ? styles.activeTab : ''}>A Existente</button>
                    <button onClick={() => setActiveTab('excel')} className={activeTab === 'excel' ? styles.activeTab : ''}>Desde Excel</button>
                </div>

                {activeTab === 'manual' && (
                    <div className="form-section">
                        <h3>Agregar insumo manualmente</h3>
                        <label className="info-label">Nombre<input value={manualNombre} onChange={e => setManualNombre(e.target.value)} type="text" className="info-block" /></label>
                        <label className="info-label">Categoría<input value={manualCategoria} onChange={e => setManualCategoria(e.target.value)} type="text" className="info-block" /></label>
                        <label className="info-label">Unidad de Medida
                            <select value={manualUnidad} onChange={e => setManualUnidad(e.target.value)} className="info-block">
                                <option value="Unidad">Unidad</option><option value="KG">KG</option><option value="Litro">Litro</option><option value="Botellas">Botellas</option>
                            </select>
                        </label>
                        <button onClick={handleSaveManual} className="info-block">Guardar nuevo insumo</button>
                    </div>
                )}

                {activeTab === 'existing' && (
                    <div className="form-section">
                        <h3>Agregar stock a insumo existente</h3>
                        <label className="info-label">Buscar producto
                            <select value={insumoExistenteId} onChange={e => setInsumoExistenteId(e.target.value)} className="info-block">
                                <option value="">-- Selecciona un insumo --</option>
                                {inventario.map(item => <option key={item.idInventario} value={item.idInventario}>{item.nombreProducto}</option>)}
                            </select>
                        </label>
                        <label className="info-label">Cantidad a agregar
                            <input value={cantidadExistente} onChange={e => setCantidadExistente(e.target.value)} type="number" min="1" className="info-block" />
                        </label>
                        <button onClick={handleSaveExisting} className="info-block">Agregar Stock</button>
                    </div>
                )}
                
                {activeTab === 'excel' && (
                     <div className="form-section">
                        <h3>Cargar inventario desde Excel</h3>
                        <label className="info-label">Cargar archivo (.xlsx, .xls)
                            <input type="file" onChange={handleFileChange} className="info-block" accept=".xlsx, .xls, .csv" />
                        </label>
                        <button onClick={handleFileUpload} className="info-block">Cargar desde Archivo</button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default AddInsumoModal;