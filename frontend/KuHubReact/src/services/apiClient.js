// src/services/apiClient.js

// La URL base de tu nuevo API Gateway
const API_BASE_URL = 'http://localhost:8095';

const apiClient = async (endpoint, options = {}) => {
    try {
        // Aseguramos que el endpoint comience con una barra inclinada
        const url = `${API_BASE_URL}${endpoint.startsWith('/') ? '' : '/'}${endpoint}`;
        
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            },
            ...options,
        });

        if (!response.ok) {
            const errorBody = await response.json().catch(() => ({ message: response.statusText }));
            throw new Error(errorBody.message || `Error del servidor: ${response.status}`);
        }
        
        if (response.status === 204) { // No Content
            return {};
        }
        
        return await response.json();
    } catch (error) {
        console.error(`Error en la petición a ${endpoint}:`, error);
        throw error;
    }
};

export default apiClient;