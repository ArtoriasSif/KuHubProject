import { useState, useEffect } from 'react';
import kuHubLogo from '../../assets/KüHubLogoWBG.png';
import ThemeButton from '../../components/ThemeButton/ThemeButton.jsx';
import BackButton from '../../components/BackButton/BackButton.jsx';
import styles from './SolicitudPage.module.css';

// Endpoints de los microservicios, verificados con tu imagen de puertos
const PRODUCTO_ENDPOINT = 'http://localhost:8081/api/v1/producto';
const SOLICITUD_DOCENTE_ENDPOINT = 'http://localhost:8083/api/v1/solicituddocente';
const DETALLE_PEDIDO_ENDPOINT = 'http://localhost:8082/api/v1/detalleproductosolicitud';

function SolicitudPage() {
    const [formData, setFormData] = useState({
        docente: 'Daniel Ojeda',
        asignatura: '',
        seccion: '',
        taller: '',
        numeroSemana: 1,
        cantidadPersonas: 20,
        descripcionSemana: '',
        fecha: new Date().toISOString().split('T')[0],
        hora: '08:00',
    });
    const [asignaturas, setAsignaturas] = useState([]);
    const [seccionesDisponibles, setSeccionesDisponibles] = useState([]);
    const [inventario, setInventario] = useState([]);
    const [pedidoActual, setPedidoActual] = useState([]);
    const [pedidosHechos, setPedidosHechos] = useState([]);
    const [productoSeleccionado, setProductoSeleccionado] = useState('');
    const [cantidadProducto, setCantidadProducto] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [enviando, setEnviando] = useState(false);

    useEffect(() => {
        const asignaturasGuardadas = JSON.parse(localStorage.getItem('asignaturas')) || [];
        setAsignaturas(asignaturasGuardadas);

        const cargarDatos = async () => {
            setLoading(true);
            const errores = [];

            // --- Llamada 1: Cargar Inventario (Productos) ---
            try {
                const inventarioRes = await fetch(PRODUCTO_ENDPOINT);
                if (!inventarioRes.ok) {
                    throw new Error(`Servicio de productos no disponible (status: ${inventarioRes.status})`);
                }
                const inventarioData = await inventarioRes.json();
                setInventario(inventarioData.map(p => ({ ...p, codigo: p.id || p.idProducto, nombre: p.nombre || p.nombreProducto })));
            } catch (err) {
                console.error("Fallo al cargar inventario:", err);
                errores.push("No se pudo cargar la lista de productos.");
                setInventario([]);
            }

            // --- Llamada 2: Cargar Historial (Solicitudes) ---
            try {
                const pedidosRes = await fetch(`${SOLICITUD_DOCENTE_ENDPOINT}/detalles`);
                if (!pedidosRes.ok) {
                    throw new Error(`Servicio de solicitudes no disponible (status: ${pedidosRes.status})`);
                }
                const pedidosData = await pedidosRes.json();
                setPedidosHechos(pedidosData);
            } catch (err) {
                console.error("Fallo al cargar historial de pedidos:", err);
                errores.push("No se pudo cargar el historial de pedidos.");
                setPedidosHechos([]);
            }

            if (errores.length > 0) {
                setError(errores.join(' '));
            } else {
                setError(null);
            }

            setLoading(false);
        };

        cargarDatos();
    }, []);

    useEffect(() => {
        if (formData.asignatura) {
            const seccionesUnicas = [...new Set(asignaturas.filter(a => a.nombre === formData.asignatura).map(s => s.seccion))];
            setSeccionesDisponibles(seccionesUnicas);
            setFormData(prev => ({ ...prev, seccion: '', taller: '' }));
        } else {
            setSeccionesDisponibles([]);
        }
    }, [formData.asignatura]);
    
    useEffect(() => {
        if (formData.seccion) {
            const info = asignaturas.find(a => a.nombre === formData.asignatura && a.seccion === formData.seccion);
            setFormData(prev => ({ ...prev, taller: info?.taller || '' }));
        }
    }, [formData.seccion]);

    const handleFormChange = (e) => {
        const { id, value, type } = e.target;
        setFormData(prev => ({ ...prev, [id]: type === 'number' ? parseInt(value, 10) : value }));
    };

    const handleAgregarProducto = () => {
        if (!productoSeleccionado) return alert('Selecciona un producto.');
        const prodEnInventario = inventario.find(p => p.codigo == productoSeleccionado);
        if (pedidoActual.find(p => p.idProducto === prodEnInventario.codigo)) return alert('Producto ya agregado.');
        setPedidoActual(prev => [...prev, { idProducto: prodEnInventario.codigo, nombreProducto: prodEnInventario.nombre, cantidadUnidadMedida: parseFloat(cantidadProducto) }]);
        setProductoSeleccionado('');
        setCantidadProducto(1);
    };

    const handleQuitarProducto = (idProducto) => setPedidoActual(prev => prev.filter(p => p.idProducto !== idProducto));

    const handleRealizarPedido = async () => {
        if (pedidoActual.length === 0) return alert('El pedido está vacío.');
        
        const datosSolicitud = {
            numeroSemana: formData.numeroSemana,
            numeroTaller: parseInt(formData.taller, 10),
            cantidadPersonas: formData.cantidadPersonas,
            descripcionSemana: formData.descripcionSemana.trim(),
            sesion: formData.seccion,
            nombreAsignatura: formData.asignatura,
            fechaProgramada: formData.fecha,
            estado: "Pendiente"
        };
        
        for (const [key, value] of Object.entries(datosSolicitud)) {
            if (value === null || value === '' || Number.isNaN(value)) {
                return alert(`El campo '${key}' está vacío o es inválido.`);
            }
        }
        
        try {
            setEnviando(true);
            const responseSolicitud = await fetch(SOLICITUD_DOCENTE_ENDPOINT, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datosSolicitud),
            });
            if (!responseSolicitud.ok) throw new Error(`Error al crear la solicitud: ${await responseSolicitud.text()}`);
            
            const solicitudGuardada = await responseSolicitud.json();
            const idSolicitudDocente = solicitudGuardada.id || solicitudGuardada.idSolicitudDocente;

            const promesasDetalles = pedidoActual.map(item => {
                const detalle = { idSolicitudDocente, idProducto: item.idProducto, cantidadUnidadMedida: item.cantidadUnidadMedida };
                return fetch(DETALLE_PEDIDO_ENDPOINT, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(detalle),
                });
            });

            const responsesDetalles = await Promise.all(promesasDetalles);
            for (const res of responsesDetalles) {
                if (!res.ok) throw new Error(`Error al guardar un detalle del pedido: ${await res.text()}`);
            }

            alert('¡Pedido realizado con éxito!');
            setPedidoActual([]);
            // Recargamos los datos para mostrar el nuevo pedido en el historial
            cargarDatos();
        } catch (err) {
            console.error('Error en el proceso de pedido:', err);
            alert(`No se pudo completar el pedido. Error: ${err.message}`);
        } finally {
            setEnviando(false);
        }
    };

    if (loading) return <div>Cargando...</div>;

    return (
        <>
            <div className="header">
                <h1>Solicitud de Materias Primas</h1>
                <img className="KHlogo" src={kuHubLogo} alt="KüHub logo" />
            </div>
            <ThemeButton />
            <BackButton />

            <div className="principal-container" id={styles.solicitudContainer}>
                {error && <div className={styles.errorMessage}><strong>Atención:</strong> {error}</div>}

                <div className={styles.datosTotales}>
                    <div className={styles.colIzquierda}>
                        <label className="info-label">Docente
                            <select id="docente" value={formData.docente} onChange={handleFormChange} className="info-block">
                                <option>Daniel Ojeda</option>
                            </select>
                        </label>
                        <button className="info-block">Agregar Docente</button>

                        <div className="fila-doble">
                            <label className="info-label">Asignatura
                                <select id="asignatura" value={formData.asignatura} onChange={handleFormChange} className="info-block">
                                    <option value="">Seleccione una asignatura</option>
                                    {[...new Set(asignaturas.map(a => a.nombre))].map(nombre => (
                                        <option key={nombre} value={nombre}>{nombre}</option>
                                    ))}
                                </select>
                            </label>
                            <label className="info-label">Sección
                                <select id="seccion" value={formData.seccion} onChange={handleFormChange} className="info-block" disabled={!formData.asignatura}>
                                    <option value="">Seleccione una sección</option>
                                    {seccionesDisponibles.map(seccion => (
                                        <option key={seccion} value={seccion}>{seccion}</option>
                                    ))}
                                </select>
                            </label>
                            <label className="info-label">Taller
                                <input id="taller" value={formData.taller} className="info-block" type="text" readOnly />
                            </label>
                        </div>
                        <div className="fila-doble">
                            <label className="info-label">N° Semana
                                <input id="numeroSemana" value={formData.numeroSemana} onChange={handleFormChange} className="info-block" type="number" min="1" />
                            </label>
                            <label className="info-label">N° Personas
                                <select id="cantidadPersonas" value={formData.cantidadPersonas} onChange={handleFormChange} className="info-block">
                                    <option value="20">20</option>
                                    <option value="40">40</option>
                                </select>
                            </label>
                        </div>
                        <label className="info-label">Descripción de la Semana
                            <textarea id="descripcionSemana" value={formData.descripcionSemana} onChange={handleFormChange} className="info-block" rows="2" placeholder="Ej: Introducción a masas quebradas"></textarea>
                        </label>
                    </div>
                    <div className={styles.colDerecha}>
                        <label className="info-label">Fecha
                            <input id="fecha" value={formData.fecha} onChange={handleFormChange} className="info-block" type="date" />
                        </label>
                        <label className="info-label">Hora
                            <input id="hora" value={formData.hora} onChange={handleFormChange} className="info-block" type="time" />
                        </label>
                    </div>
                </div>

                <div className="section-title">Productos Solicitados</div>
                <div className={styles.pedidoForm}>
                    <select value={productoSeleccionado} onChange={e => setProductoSeleccionado(e.target.value)} className="info-block" disabled={inventario.length === 0}>
                        <option value="">
                            {inventario.length > 0 ? "-- Busca y selecciona un producto --" : "Lista de productos no disponible"}
                        </option>
                        {inventario.map(item => (
                            <option key={item.codigo} value={item.codigo}>
                                {item.nombre} (Stock: {item.stock})
                            </option>
                        ))}
                    </select>
                    <input type="number" value={cantidadProducto} onChange={e => setCantidadProducto(e.target.value)} min="1" className="info-block" style={{ width: '80px' }} />
                    <button onClick={handleAgregarProducto} className="info-block" disabled={inventario.length === 0}>Agregar al Pedido</button>
                </div>

                <h3>Resumen del Pedido</h3>
                <div className={styles.pedidoActual}>
                    {pedidoActual.length === 0 ? <p>El pedido está vacío.</p> : (
                        <ul>
                            {pedidoActual.map(item => (
                                <li key={item.idProducto}>
                                    {item.nombreProducto} - Cantidad: {item.cantidadUnidadMedida}
                                    <button onClick={() => handleQuitarProducto(item.idProducto)} className={styles.btnQuitar}>Quitar</button>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
                <button onClick={handleRealizarPedido} disabled={enviando || pedidoActual.length === 0} className="info-block" style={{ backgroundColor: 'var(--color-duoc-gold)' }}>
                    {enviando ? 'Enviando Pedido...' : 'Realizar Pedido'}
                </button>

                <div className="section-title">Pedidos Hechos</div>
                <div className="tabla-container">
                    <table>
                        <thead>
                            <tr><th>Semana</th><th>Asignatura</th><th>Sección</th><th>Fecha</th><th>Estado</th><th>Ítems</th></tr>
                        </thead>
                        <tbody>
                            {pedidosHechos.map((pedido, index) => (
                                <tr key={pedido.id || index}>
                                    <td>{pedido.numeroSemana}</td>
                                    <td>{pedido.nombreAsignatura}</td>
                                    <td>{pedido.sesion}</td>
                                    <td>{new Date(pedido.fechaProgramada).toLocaleDateString('es-CL')}</td>
                                    <td>{pedido.estado}</td>
                                    <td>
                                        {pedido.detalles && pedido.detalles.length > 0 ? (
                                            <ul>{pedido.detalles.map(d => <li key={d.idProducto}>{d.nombreProducto} (x{d.cantidadUnidadMedida})</li>)}</ul>
                                        ) : ( <span>N/A</span> )}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default SolicitudPage;