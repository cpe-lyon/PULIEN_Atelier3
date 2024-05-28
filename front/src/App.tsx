import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import PrivateRoute from './pages/PrivateRoute';
import Login from './pages/Login';
import CardPage from './pages/CardPage';
import Register from './pages/Register';
import AlreadyConnected from './pages/AlreadyConnected';
import { JSX } from 'react/jsx-runtime';
import Marketplace from './pages/Marketplace';
import Vitrine from '@/pages/VitrinePage';
import { useAtom } from 'jotai';
import { userCash, username } from '@/context/jotai';
import { useEffect } from 'react';
import UserService from '@/services/UserService';
import Navbar from './components/Navbar';

function App() {
    const [usernameFromContext, setUsername] = useAtom(username);
    const [usercashFromContext, setUsercash] = useAtom(userCash);
    useEffect(() => {
        const getData = async () => {
            UserService.getUser().then(userData => {
                setUsername(userData.login);
                setUsercash(userData.cash);
            });
        };
        getData();
    }, []);

    const wrapPrivateRoute = (element: JSX.Element) => {
        return (
            <PrivateRoute>
                {element}
            </PrivateRoute>
        );
    };
    return (
        <div className='bg-slate-700 min-h-screen flex flex-col'>
            <Router>
                {usernameFromContext ? <Navbar username={usernameFromContext} cash={usercashFromContext} /> : <></>}
                <div className="pt-16 flex-1"> 
                    <Routes>
                        <Route path="/authentified" element={<AlreadyConnected />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/" element={<Navigate to="/card" />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/card" element={wrapPrivateRoute(<CardPage />)} />
                        <Route path="/marketplace" element={wrapPrivateRoute(<Marketplace />)} />
                        <Route path="/vitrine" element={wrapPrivateRoute(<Vitrine />)} />
                    </Routes>
                </div>
            </Router>
        </div>
    );
}

export default App;
