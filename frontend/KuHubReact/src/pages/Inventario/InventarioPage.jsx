import React, { useState, useEffect } from 'react';
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
    const [manualCantidad, setManualCantidad] = useState(1);
    const [excelFile, setExcelFile] = useState(null);
    const [sugerencias, setSugerencias] = useState([]);
    const [mostrarSugerencias, setMostrarSugerencias] = useState(false);
    const [unidadBloqueada, setUnidadBloqueada] = useState(false);

    // Extraer nombres únicos de productos del inventario para sugerencias
    const nombresProductos = React.useMemo(() => {
        return [...new Set(inventario.map(item => item.nombreProducto || item.nombre).filter(Boolean))];
    }, [inventario]);

    const handleNombreChange = (e) => {
        const valor = e.target.value;
        setManualNombre(valor);
        
        if (valor.length > 1) {
            // Filtrar sugerencias basadas en lo que el usuario escribe
            const sugerenciasFiltradas = nombresProductos.filter(nombre => 
                nombre.toLowerCase().includes(valor.toLowerCase())
            );
            setSugerencias(sugerenciasFiltradas);
            setMostrarSugerencias(true);
        } else {
            setSugerencias([]);
            setMostrarSugerencias(false);
        }
    };

    const seleccionarSugerencia = (sugerencia) => {
        setManualNombre(sugerencia);
        setMostrarSugerencias(false);
        
        // Buscar el producto en el inventario para autocompletar otros campos
        const productoEncontrado = inventario.find(item => 
            (item.nombreProducto === sugerencia || item.nombre === sugerencia)
        );
        
        if (productoEncontrado) {
            setManualCategoria(productoEncontrado.categoria || '');
            setManualUnidad(productoEncontrado.unidadMedida || 'Unidad');
            // Bloquear la unidad de medida para que no se pueda cambiar
            setUnidadBloqueada(true);
        } else {
            // Si no se encuentra el producto, desbloquear la unidad de medida
            setUnidadBloqueada(false);
        }
    };

    const handleSaveManual = () => {
        if (!manualNombre || !manualUnidad || !manualCategoria) return alert('Completa todos los campos.');
        if (manualCantidad <= 0) return alert('La cantidad debe ser mayor a 0.');
        onSave.manual({
            nombre: manualNombre,
            unidadMedida: manualUnidad,
            categoria: manualCategoria,
            stock: parseFloat(manualCantidad) // Usamos la cantidad ingresada como stock inicial
        });
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
                    <button onClick={() => setActiveTab('excel')} className={activeTab === 'excel' ? modalStyles.activeTab : ''}>Desde Excel</button>
                </div>

                {activeTab === 'manual' && (
                    <div className="form-section">
                        <h3>Agregar insumo manualmente</h3>
                        <label className="info-label">
                            Nombre
                            <div className={styles.searchContainer}>
                                <input 
                                    value={manualNombre} 
                                    onChange={handleNombreChange} 
                                    type="text" 
                                    className="info-block" 
                                    placeholder="Buscar o agregar nuevo insumo"
                                    onFocus={() => manualNombre.length > 1 && setMostrarSugerencias(true)}
                                    onBlur={() => setTimeout(() => setMostrarSugerencias(false), 200)}
                                />
                                {mostrarSugerencias && sugerencias.length > 0 && (
                                    <div className={styles.suggestionsContainer}>
                                        {sugerencias.map((sugerencia, index) => (
                                            <div 
                                                key={index}
                                                className={styles.suggestionItem}
                                                onMouseDown={() => seleccionarSugerencia(sugerencia)}
                                            >
                                                {sugerencia}
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        </label>
                        
                        <div className={styles.formField}>
                            <label className="info-label">Categoría
                                <input 
                                    value={manualCategoria} 
                                    onChange={e => setManualCategoria(e.target.value)} 
                                    type="text" 
                                    className="info-block" 
                                />
                            </label>
                        </div>
                        
                        <div className={styles.formField}>
                            <label className="info-label">Unidad de Medida
                                <input 
                                    type="text"
                                    value={manualUnidad} 
                                    onChange={e => setManualUnidad(e.target.value)} 
                                    className="info-block"
                                    disabled={unidadBloqueada}
                                    readOnly={unidadBloqueada}
                                />
                            </label>
                        </div>
                        
                        <div className={styles.formField}>
                            <label className="info-label">Cantidad
                                <input 
                                    value={manualCantidad} 
                                    onChange={e => setManualCantidad(e.target.value)} 
                                    type="number" 
                                    min="1"
                                    step="any"
                                    className="info-block" 
                                />
                            </label>
                        </div>
                        <button onClick={handleSaveManual} className="info-block">Guardar nuevo insumo</button>
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
    const [nombre, setNombre] = useState(insumo.nombre);
    const [categoria, setCategoria] = useState(insumo.categoria);
    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}><h2>Editar Insumo</h2><span className={modalStyles.closeButton} onClick={onClose}>&times;</span></div>
                <label className="info-label">Nombre<input type="text" value={nombre} onChange={e => setNombre(e.target.value)} className="info-block" /></label>
                <label className="info-label">Categoría<input type="text" value={categoria} onChange={e => setCategoria(e.target.value)} className="info-block" /></label>
                <button onClick={() => onSave({ ...insumo, nombre, categoria })} className="info-block">Guardar Cambios</button>
            </div>
        </div>
    );
}

function QuitarInsumoModal({ insumo, onClose, onSave }) {
    const [cantidad, setCantidad] = useState(1);
    const handleSave = () => {
        const cantidadFloat = parseFloat(cantidad);
        if (cantidadFloat <= 0 || cantidadFloat > insumo.stock) return alert("Cantidad inválida.");
        onSave(insumo.id, cantidadFloat);
        onClose();
    };
    return (
        <div className={modalStyles.modalOverlay} onClick={onClose}>
            <div className={modalStyles.modalContent} onClick={e => e.stopPropagation()}>
                <div className={modalStyles.modalHeader}><h2>Quitar Stock de: {insumo.nombre}</h2><span className={modalStyles.closeButton} onClick={onClose}>&times;</span></div>
                <label className="info-label">Cantidad a Quitar (Stock actual: {insumo.stock})
                    <input type="number" value={cantidad} onChange={e => setCantidad(e.target.value)} min="1" max={insumo.stock} className="info-block" />
                </label>
                <button onClick={handleSave} className="info-block borrar-btn">Confirmar</button>
            </div>
        </div>
    );
}

// En InventarioPage.jsx

// En InventarioPage.jsx

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
                <div className={modalStyles.modalHeader}>
                    <h2>Historial de: {insumo.nombreProducto}</h2>
                    <span className={modalStyles.closeButton} onClick={onClose}>&times;</span>
                </div>
                <div className="tabla-container">
                    <table>
                        <thead><tr><th>Fecha</th><th>Tipo</th><th>Cantidad</th></tr></thead>
                        <tbody>
                            {loading ? (<tr><td colSpan="3">Cargando...</td></tr>) :
                                historial.length > 0 ? (
                                    historial.map((registro) => (
                                        // --- INICIO DE LA CORRECCIÓN ---
                                        <tr key={registro.idMovimiento}>
                                            <td>{new Date(registro.fechaMovimiento).toLocaleDateString('es-CL')}</td>
                                            <td>{registro.tipoMovimiento}</td>
                                            <td>{registro.cantidadMovimiento}</td>
                                        </tr>
                                        // --- FIN DE LA CORRECCIÓN ---
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

    const handleEditarInsumo = async (insumoActualizado) => {
        try {
            const data = await apiClient(`/api/v1/inventario/${insumoActualizado.id}`, {
                method: 'PUT', body: JSON.stringify(insumoActualizado)
            });
            setInventario(prev => prev.map(item => item.id === insumoActualizado.id ? data : item));
        } catch (err) {
            alert(`Error al modificar el insumo: ${err.message}`);
        }
    };

    const handleQuitarCantidad = async (insumoId, cantidad) => {
        try {
            const data = await apiClient(`/api/v1/movimiento/salida`, {
                method: 'POST', body: JSON.stringify({ productoId: insumoId, cantidad: cantidad })
            });
            setInventario(prev => prev.map(item => item.id === insumoId ? data : item));
        } catch (err) {
            alert(`Error al quitar stock: ${err.message}`);
        }
    };

    const handleExcelUpload = async (workbook) => {
    try {
        if (!workbook || !workbook.SheetNames) {
            return alert("El archivo Excel no pudo ser procesado.");
        }

        const insumosParaCargar = [];

        // 1. Recorremos CADA HOJA del archivo Excel
        workbook.SheetNames.forEach(sheetName => {
            const sheet = workbook.Sheets[sheetName];
            // Ignoramos las hojas ocultas
            if (sheet.Props && sheet.Props.Hidden) {
                console.log(`Hoja '${sheetName}' omitida por estar oculta.`);
                return;
            }
            
            const datosDeLaHoja = XLSX.utils.sheet_to_json(sheet);
            const categoria = sheetName;

            // --- INICIO DE LA LÓGICA DE PARSEO ---
            datosDeLaHoja.forEach(filaDelExcel => {
                // Función interna para buscar una propiedad sin importar mayúsculas/minúsculas
                const obtenerValor = (fila, posiblesNombres) => {
                    for (const nombre of posiblesNombres) {
                        const claveReal = Object.keys(fila).find(k => k.toLowerCase().trim() === nombre.toLowerCase());
                        if (claveReal) return fila[claveReal];
                    }
                    return null;
                };

                // Buscamos los valores en las columnas correspondientes
                const nombreInsumo = obtenerValor(filaDelExcel, ['insumo', 'nombre']);
                const cantidadTotal = obtenerValor(filaDelExcel, ['cantidad total', 'stock', 'inicial']);
                const unidadMedida = obtenerValor(filaDelExcel, ['u / m', 'unidad de medida']);
                
                // Si la fila no tiene un nombre de insumo, la ignoramos
                if (!nombreInsumo) {
                    console.warn("Se omitió una fila del Excel por no tener nombre de insumo:", filaDelExcel);
                    return;
                }

                // Creamos el objeto que enviaremos al backend
                insumosParaCargar.push({
                    nombre: nombreInsumo,
                    unidadMedida: unidadMedida || 'Unidad', // El backend espera 'unidadMedida'
                    stock: parseFloat(cantidadTotal) || 0, // El backend espera 'stock'
                    categoria: categoria,
                });
            });
            // --- FIN DE LA LÓGICA DE PARSEO ---
        });

        if (insumosParaCargar.length === 0) {
            return alert("No se encontraron insumos válidos para cargar en el archivo.");
        }

        // 2. Enviamos la lista completa de insumos al backend
        await apiClient('/api/v1/inventario/lote', { 
            method: 'POST', 
            body: JSON.stringify(insumosParaCargar) 
        });
        
        alert(`Se han cargado ${insumosParaCargar.length} insumos con éxito.`);
        
        // 3. Recargamos el inventario para mostrar los nuevos datos
        cargarInventario();
        
    } catch (err) {
        alert(`Error al cargar el archivo: ${err.message}`);
    }
};

// --- LÓGICA DE FILTRADO Y BÚSQUEDA ---
    const categorias = ['Todos', ...new Set(inventario.map(item => item.categoria).filter(Boolean))];

    const inventarioFiltrado = inventario.filter(item => 
        (categoriaActual === 'Todos' || item.categoria === categoriaActual) &&
        (
            // --- INICIO DE LA CORRECCIÓN ---
            // Usamos los nombres de propiedad correctos del backend
            (item.nombreProducto?.toLowerCase().includes(terminoBusqueda.toLowerCase())) ||
            (item.idInventario?.toString().includes(terminoBusqueda))
            // --- FIN DE LA CORRECCIÓN ---
        )
    );

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
                    <button 
                    onClick={() => setIsAddModalOpen(true)} 
                    className={`login-button ${styles.fullWidthButton}`} 
                    id="agregar-button">
                    Agregar Insumo
                    </button>
                    <button className="info-block borrar-btn">Vaciar Inventario</button>
                </div>
                <input
                    className="info-block" type="text" placeholder="Buscar por nombre o ID"
                    value={terminoBusqueda} onChange={(e) => setTerminoBusqueda(e.target.value)}
                />
                <div className={styles.pestañasContainer}>
                    {categorias.map(cat => (<button key={cat} className={`${styles.pestañaBtn} ${categoriaActual === cat ? styles.active : ''}`} onClick={() => setCategoriaActual(cat)}>{cat}</button>))}
                </div>
                <div className="section-title">Inventario Actual</div>
                <div className="tabla-container">
                    <table>
                        <thead>
                            <tr><th>CÓDIGO</th><th>Unidad</th><th>Insumo</th><th>Stock</th><th>Categoría</th><th>Historial</th><th>Acciones</th></tr>
                        </thead>
                        {/* ... dentro de la función InventarioPage, en el return ... */}
                            <tbody>
                                {inventarioFiltrado.length > 0 ? (
                                    inventarioFiltrado.map(item => (
                                        // --- INICIO DE LA CORRECCIÓN ---
                                        <tr key={item.idInventario}> {/* Usamos el ID correcto para el key */}
                                            <td>{item.idInventario}</td>
                                            <td>{item.unidadMedida}</td>
                                            <td>{item.nombreProducto}</td>
                                            <td>{item.totalInventario}</td>
                                            {/* La categoría no viene en el JSON, podemos mostrar un guion o quitar la columna */}
                                            <td>{item.categoria || '-'}</td>
                                            <td 
                                                className={styles.historialCell} 
                                                onClick={() => setInsumoParaVerHistorial(item)}
                                            >
                                                Ver Historial
                                            </td>
                                            <td>
                                                <button className="eliminar-btn" onClick={() => setInsumoParaQuitar(item)}>Quitar</button>
                                                <button className="info-block" onClick={() => setInsumoParaEditar(item)}>Modificar</button>
                                            </td>
                                        </tr>
                                        // --- FIN DE LA CORRECCIÓN ---
                                    ))
                                ) : (
                                    <tr><td colSpan="7">No hay datos que coincidan.</td></tr>
                                )}
                            </tbody>
                    </table>
                </div>
            </div>

            {isAddModalOpen && <AddInsumoModal inventario={inventario} onClose={() => setIsAddModalOpen(false)} onSave={{manual: handleAgregarInsumo, excel: handleExcelUpload}} />}
            {insumoParaEditar && <EditInsumoModal insumo={insumoParaEditar} onClose={() => setInsumoParaEditar(null)} onSave={handleEditarInsumo} />}
            {insumoParaQuitar && <QuitarInsumoModal insumo={insumoParaQuitar} onClose={() => setInsumoParaQuitar(null)} onSave={handleQuitarCantidad} />}
            {insumoParaVerHistorial && <HistorialModal insumo={insumoParaVerHistorial} onClose={() => setInsumoParaVerHistorial(null)} />}
        </>
    );
}

export default InventarioPage;