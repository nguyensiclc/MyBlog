/**
 * Base URL của backend API.
 * Production: set VITE_API_URL khi build (e.g. VITE_API_URL=http://your-ec2-ip:8080 npm run build).
 * Development: mặc định http://localhost:8080
 */
export const API_BASE = import.meta.env.VITE_API_URL || 'http://localhost:8080';
