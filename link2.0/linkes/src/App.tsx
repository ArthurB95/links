import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import { LandingPage } from './pages/LandingPage';
import { LoginPage } from './pages/LoginPage';
import { Dashboard } from './pages/Dashboard';
import { BioPage } from './pages/BioPage';
import { LinkShortenerPage } from './pages/LinkShortenerPage';
import { QRCodePage } from './pages/QRCodePage';
import { PublicProfilePage } from './pages/PublicProfilePage';

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route 
          path="/login" 
          element={
            isAuthenticated ? <Navigate to="/dashboard" /> : <LoginPage onLogin={() => setIsAuthenticated(true)} />
          } 
        />
        <Route 
          path="/dashboard" 
          element={
            isAuthenticated ? <Dashboard /> : <Navigate to="/login" />
          } 
        />
        <Route 
          path="/bio" 
          element={
            isAuthenticated ? <BioPage /> : <Navigate to="/login" />
          } 
        />
        <Route 
          path="/links" 
          element={
            isAuthenticated ? <LinkShortenerPage /> : <Navigate to="/login" />
          } 
        />
        <Route 
          path="/qrcode" 
          element={
            isAuthenticated ? <QRCodePage /> : <Navigate to="/login" />
          } 
        />
        {/* Public Profile Routes */}
        <Route path="/preview/:username" element={<PublicProfilePage />} />
        <Route path="/:username" element={<PublicProfilePage />} />
      </Routes>
    </Router>
  );
}