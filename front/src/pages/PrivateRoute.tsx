import authProvider from '@/services/AuthProvider';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }:any) => {
  const isAuthenticated = authProvider.checkAuth(); 

  return isAuthenticated ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
