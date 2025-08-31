// src/routes/AppRouter.jsx

import { Routes, Route } from 'react-router-dom';
import LoginPage from '../pages/Login/LoginPage';
import HubAdminPage from '../pages/HubAdmin/HubAdminPage';
import SolicitudPage from '../pages/Solicitud/SolicitudPage';
import InventarioPage from '../pages/Inventario/InventarioPage';
import AccountPage from '../pages/Account/AccountPage';

function AppRouter() {
  return (
    // Solo se necesita <Routes> aquí
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/admin/hub" element={<HubAdminPage />} />
      <Route path="/docente/solicitud" element={<SolicitudPage />} />
      <Route path="/admin/inventario" element={<InventarioPage />} />
      <Route path="/account" element={<AccountPage />} />
      {/* ...tus otras rutas... */}
    </Routes>
  );
}

export default AppRouter;