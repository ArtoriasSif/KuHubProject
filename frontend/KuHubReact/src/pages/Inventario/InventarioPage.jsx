import React, { useState, useEffect, useMemo } from 'react';
import * as XLSX from 'xlsx';
import kuHubLogo from '../../assets/KüHubLogoWBG.png';
import ThemeButton from '../../components/ThemeButton/ThemeButton.jsx';
import BackButton from '../../components/BackButton/BackButton.jsx';
import styles from './InventarioPage.module.css';
import modalStyles from '../../components/Modals/Modal.module.css';
import apiClient from '../../services/apiClient';

// --- COMPONENTES MODALES ---
// Nota: En un proyecto real, es una mejor práctica mover cada componente a su propio archivo.

function AddInsumoModal({ inventario, onClose, onSave }) {
    const [activeTab, setActiveTab] = useState('manual');
    const [manualNombre, setManualNombre] = useState('');
    const [manualUnidad, setManualUnidad] = useState('Unidad');
    const [manualCategoria, setManualCategoria] = useState('');
    const [insumoExistenteId, setInsumoExistenteId] = useState('');
    const [cantidadExistente, setCantidadExistente] = useState(1);
    const [excelFile, setExcelFile] = useState(null);

    const handleSaveManual = () => {
        if (!manualNombre || !manualUnidad || !manualCategoria) return alert('Completa todos los campos.');
        onSave.manual({
            nombreProducto: manualNombre,
            unidadMedida: manualUnidad,
            categoria: manualCategoria,
            stock: 0
        });
        onClose();
    };

    const handleSaveExisting = () => {
        if (!insumoExistenteId || !cantidadExistente) return alert('Selecciona un insumo y una cantidad.');
        onSave.existing(parseInt(insumoExistenteId, 10), parseFloat(cantidadExistente));
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
                onSave.excel(workbook);
                onClose();
            } catch (error) {
                alert("Error al leer el archivo Excel.");
                console.error("Error procesando Excel:", error);
            }
        };
    };

    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}>
                    <h2>Agregar Insumo</h2>
                    <span className={modalStyles.closeButton} onClick={onClose}>&times;</span>
                </div>
                <div className={modalStyles.tabContainer}>
                    <button onClick={() => setActiveTab('manual')} className={activeTab === 'manual' ? modalStyles.activeTab : ''}>Manual</button>
                    <button onClick={() => setActiveTab('existing')} className={activeTab === 'existing' ? modalStyles.activeTab : ''}>A Existente</button>
                    <button onClick={() => setActiveTab('excel')} className={activeTab === 'excel' ? modalStyles.activeTab : ''}>Desde Excel</button>
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

function EditInsumoModal({ insumo, onClose, onSave }) {
    const [nombre, setNombre] = useState(insumo.nombreProducto);
    const [categoria, setCategoria] = useState(insumo.categoria);

    const handleSave = () => {
        onSave({ ...insumo, nombreProducto: nombre, categoria });
        onClose();
    };

    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}><h2>Editar Insumo</h2><span className={modalStyles.closeButton} onClick={onClose}>&times;</span></div>
                <label className="info-label">Nombre<input type="text" value={nombre} onChange={e => setNombre(e.target.value)} className="info-block" /></label>
                <label className="info-label">Categoría<input type="text" value={categoria} onChange={e => setCategoria(e.target.value)} className="info-block" /></label>
                <button onClick={handleSave} className="info-block">Guardar Cambios</button>
            </div>
        </div>
    );
}

function QuitarInsumoModal({ insumo, onClose, onSave }) {
    const [cantidad, setCantidad] = useState(1);
    const handleSave = () => {
        const cantidadFloat = parseFloat(cantidad);
        if (cantidadFloat <= 0 || cantidadFloat > insumo.totalInventario) return alert("Cantidad inválida.");
        onSave(insumo.idInventario, cantidadFloat);
        onClose();
    };
    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}><h2>Quitar Stock de: {insumo.nombreProducto}</h2><span className={modalStyles.closeButton} onClick={onClose}>&times;</span></div>
                <label className="info-label">Cantidad a Quitar (Stock actual: {insumo.totalInventario})
                    <input type="number" value={cantidad} onChange={e => setCantidad(e.target.value)} min="1" max={insumo.totalInventario} className="info-block" />
                </label>
                <button onClick={handleSave} className="info-block borrar-btn">Confirmar</button>
            </div>
        </div>
    );
}

function HistorialModal({ insumo, onClose }) {
    const [historial, setHistorial] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchHistorial = async () => {
            if (!insumo || !insumo.idInventario) return;
            try {
                const data = await apiClient(`/api/v1/movimiento/inventario/${insumo.idInventario}`);
                setHistorial(data);
            } catch (error) {
                console.error("Error al cargar historial:", error);
                setHistorial([]);
            } finally {
                setLoading(false);
            }
        };
        fetchHistorial();
    }, [insumo]);

    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}><h2>Historial de: {insumo.nombreProducto}</h2><span className={modalStyles.closeButton} onClick={onClose}>&times;</span></div>
                <div className="tabla-container">
                    <table>
                        <thead><tr><th>Fecha</th><th>Tipo</th><th>Cantidad</th></tr></thead>
                        <tbody>
                            {loading ? (<tr><td colSpan="3">Cargando...</td></tr>) :
                                historial.length > 0 ? (
                                    historial.map((registro) => (
                                        <tr key={registro.idMovimiento}>
                                            <td>{new Date(registro.fechaMovimiento).toLocaleDateString('es-CL')}</td>
                                            <td>{registro.tipoMovimiento}</td>
                                            <td>{registro.cantidadMovimiento}</td>
                                        </tr>
                                    ))
                                ) : (<tr><td colSpan="3">No hay historial para este insumo.</td></tr>)
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}


// --- COMPONENTE PRINCIPAL DE LA PÁGINA ---
function InventarioPage() {
    const [inventario, setInventario] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [categoriaActual, setCategoriaActual] = useState('Todos');
    const [terminoBusqueda, setTerminoBusqueda] = useState('');
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [insumoParaEditar, setInsumoParaEditar] = useState(null);
    const [insumoParaQuitar, setInsumoParaQuitar] = useState(null);
    const [insumoParaVerHistorial, setInsumoParaVerHistorial] = useState(null);

    

    const cargarInventario = async () => {
        try {
            setLoading(true); setError(null);
            const data = await apiClient('/api/v1/inventario');
            setInventario(data);
        } catch (err) {
            setError("No se pudo cargar el inventario desde el servidor.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        cargarInventario();
    }, []);

    const handleAgregarInsumo = async (nuevoInsumo) => {
        try {
            const insumoGuardado = await apiClient('/api/v1/inventario', {
                method: 'POST', body: JSON.stringify(nuevoInsumo)
            });
            setInventario(prev => [...prev, insumoGuardado]);
        } catch (err) {
            alert(`Error al agregar el insumo: ${err.message}`);
        }
    };
    
    const handleAgregarExistente = async (idInventario, cantidad) => {
        try {
            const movimiento = { inventarioId: idInventario, cantidad, tipoMovimiento: 'ENTRADA' };
            const insumoActualizado = await apiClient('/api/v1/movimiento/entrada', {
                method: 'POST', body: JSON.stringify(movimiento)
            });
            setInventario(prev => prev.map(item => item.idInventario === idInventario ? insumoActualizado : item));
        } catch (err) {
            alert(`Error al agregar stock: ${err.message}`);
        }
    };

    const handleEditarInsumo = async (insumoActualizado) => {
        try {
            const data = await apiClient(`/api/v1/inventario/${insumoActualizado.idInventario}`, {
                method: 'PUT', body: JSON.stringify(insumoActualizado)
            });
            setInventario(prev => prev.map(item => item.idInventario === insumoActualizado.idInventario ? data : item));
        } catch (err) {
            alert(`Error al modificar el insumo: ${err.message}`);
        }
    };

    const handleQuitarCantidad = async (insumoId, cantidad) => {
        try {
            const movimiento = { inventarioId: insumoId, cantidad, tipoMovimiento: 'SALIDA' };
            const insumoActualizado = await apiClient(`/api/v1/movimiento/salida`, {
                method: 'POST', body: JSON.stringify(movimiento)
            });
            setInventario(prev => prev.map(item => item.idInventario === insumoId ? insumoActualizado : item));
        } catch (err) {
            alert(`Error al quitar stock: ${err.message}`);
        }
    };
    
    const handleExcelUpload = async (workbook) => {
        try {
            const insumosParaCargar = [];
            workbook.SheetNames.forEach(sheetName => {
                const sheet = workbook.Sheets[sheetName];
                if (sheet.Props && sheet.Props.Hidden) return;
                const datosDeLaHoja = XLSX.utils.sheet_to_json(sheet);
                const categoria = sheetName;
                datosDeLaHoja.forEach(filaDelExcel => {
                    const obtenerValor = (fila, posiblesNombres) => {
                        for (const nombre of posiblesNombres) {
                            const claveReal = Object.keys(fila).find(k => k.toLowerCase().trim() === nombre.toLowerCase());
                            if (claveReal) return fila[claveReal];
                        }
                        return null;
                    };
                    const nombreInsumo = obtenerValor(filaDelExcel, ['insumo', 'nombre']);
                    if (!nombreInsumo) return;
                    insumosParaCargar.push({
                        nombreProducto: nombreInsumo,
                        unidadMedida: obtenerValor(filaDelExcel, ['u / m', 'unidad de medida']) || 'Unidad',
                        stock: parseFloat(obtenerValor(filaDelExcel, ['cantidad total', 'stock', 'inicial']) || 0),
                        categoria: categoria,
                    });
                });
            });
            if (insumosParaCargar.length === 0) return alert("No se encontraron insumos válidos para cargar.");

            await apiClient('/api/v1/inventario/lote', { 
                method: 'POST', body: JSON.stringify(insumosParaCargar) 
            });
            alert(`Se han cargado ${insumosParaCargar.length} insumos con éxito.`);
            cargarInventario();
        } catch (err) {
            alert(`Error al cargar el archivo: ${err.message}`);
        }
    };

    const handleVaciarInventario = async () => {
        if (window.confirm("¿Estás seguro de que quieres borrar todo el inventario? Esta acción no se puede deshacer.")) {
            try {
                await apiClient('/api/v1/inventario/all', { method: 'DELETE' });
                setInventario([]);
            } catch (err) {
                alert(`Error al vaciar el inventario: ${err.message}`);
            }
        }
    };
    
    const categoriasMap = new Map();
    inventario.forEach(item => {
        // Nos aseguramos que el item y la categoría existan y tengan un ID
        if (item && item.categoria && item.categoria.idCategoria) {
            // Si ya existe una categoría con este ID, se sobrescribe,
            // pero como es la misma, el resultado es una lista sin duplicados.
            categoriasMap.set(item.categoria.idCategoria, item.categoria);
        }
    });

    // Creamos el array final de pestañas, añadiendo "Todos" al principio.
    const categoriasUnicas = [
        { idCategoria: 'Todos', nombreCategoria: 'Todos' }, 
        ...Array.from(categoriasMap.values())
    ];
    
    // La lógica de filtrado que ya teníamos sigue siendo correcta.
    const inventarioFiltrado = inventario.filter(item => {
        const categoriaCoincide = categoriaActual === 'Todos' || (item.categoria && item.categoria.nombreCategoria === categoriaActual);
        const nombreCoincide = item && item.nombreProducto && item.nombreProducto.toLowerCase().includes(terminoBusqueda.toLowerCase());
        const codigoCoincide = item && item.idInventario && item.idInventario.toString().includes(terminoBusqueda);
        return categoriaCoincide && (nombreCoincide || codigoCoincide);
    });
      

    if (loading) return <div>Cargando inventario...</div>;
    if (error) return <div className={styles.errorMessage}>{error}</div>;

    return (
        <>
            <div className="header">
                <h1>Inventario</h1>
                <img className="KHlogo" src={kuHubLogo} alt="KüHub logo" />
            </div>
            <ThemeButton />
            <BackButton />

            <div className={`principal-container ${styles.inventarioContainer}`}>
                <div className={styles.actionBar}>
                    <button onClick={() => setIsAddModalOpen(true)} className="info-block">Agregar Insumo</button>
                    <button className="info-block">Exportar a Excel</button>
                    <button onClick={handleVaciarInventario} className="info-block borrar-btn">Vaciar Inventario</button>
                </div>
                <input
                    className="info-block" type="text" placeholder="Buscar por nombre o ID"
                    value={terminoBusqueda} onChange={(e) => setTerminoBusqueda(e.target.value)}
                />
                <div className={styles.pestañasContainer}>
                    {categoriasUnicas.map(cat => (
                        <button key={cat.idCategoria} className={`${styles.pestañaBtn} ${categoriaActual === cat.nombreCategoria ? styles.active : ''}`}
                            onClick={() => setCategoriaActual(cat.nombreCategoria)}>{cat.nombreCategoria}</button>
                    ))}
                </div>
                <div className="section-title">Inventario Actual</div>
                <div className="tabla-container">
                    <table>
                        <thead>
                            <tr><th>CÓDIGO</th><th>Unidad</th><th>Insumo</th><th>Stock</th><th>Categoría</th><th>Historial</th><th>Acciones</th></tr>
                        </thead>
                        <tbody>
                            {inventarioFiltrado.length > 0 ? (
                                inventarioFiltrado.map(item => (
                                    <tr key={item.idInventario}>
                                        <td>{item.idInventario}</td>
                                        <td>{item.unidadMedida}</td>
                                        <td>{item.nombreProducto}</td>
                                        <td>{item.totalInventario}</td>
                                        <td>{item.categoria?.nombreCategoria || '-'}</td>
                                        <td className={styles.historialCell} onClick={() => setInsumoParaVerHistorial(item)}>Ver Historial</td>
                                        <td>
                                            <button className="eliminar-btn" onClick={() => setInsumoParaQuitar(item)}>Quitar</button>
                                            <button className="info-block" onClick={() => setInsumoParaEditar(item)}>Modificar</button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr><td colSpan="7">No hay datos que coincidan.</td></tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>

            {isAddModalOpen && <AddInsumoModal inventario={inventario} onClose={() => setIsAddModalOpen(false)} onSave={{ manual: handleAgregarInsumo, existing: handleAgregarExistente, excel: handleExcelUpload }} />}
            {insumoParaEditar && <EditInsumoModal insumo={insumoParaEditar} onClose={() => setInsumoParaEditar(null)} onSave={handleEditarInsumo} />}
            {insumoParaQuitar && <QuitarInsumoModal insumo={insumoParaQuitar} onClose={() => setInsumoParaQuitar(null)} onSave={handleQuitarCantidad} />}
            {insumoParaVerHistorial && <HistorialModal insumo={insumoParaVerHistorial} onClose={() => setInsumoParaVerHistorial(null)} />}
        </>
    );
}

export default InventarioPage;