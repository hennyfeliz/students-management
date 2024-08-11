import { useRef, useState } from 'react';
import '../styles/Login.css'
import { useNavigate } from 'react-router-dom';
import { EyeIcon } from '../assets/icons/Icons';
import loginFetch from '../utils/Login.js'
import Cookies from 'js-cookie'
import { toast } from 'sonner';

export const Login = () => {

  const navigate = useNavigate();

  const email = useRef()
  const password = useRef()

  const [passwordViewer, setPasswordViewer] = useState(false);

  const handleSubmit = async () => {
    navigate("/users")
    loginFetch({ username: email.current.value, password: password.current.value })
      .then((response) => {
        if (response.status === 200) {
          toast.success('Inicio de sesión exitoso')

        }
        if (response.status === 401) toast.error('Error de credenciales');
        return response.json()
      })
      .then((data) => {
        if (data.token) { Cookies.set('token', data.token) }
      })
      .catch((error) => { toast.error('Error en inicio de sesión'); console.log(error) });
  }

  const handleCheckboxChange = () => {
    setPasswordViewer(!passwordViewer)
  }

  return (
    <div className='login-component'>
      <div className='login'>
        <div className="login-card">
          <div className='login-icon'>
            <h2>Bienvenido a su app</h2>
            <span>Por favor, introduzca sus datos para entrar</span>
          </div>
          <div className="login-elements">
            <span>Usuario</span>
            <input type="text" placeholder="Introduzca su usuario..." ref={email} />
            <span>Contraseña</span>
            <div className='password-input'>
              <input
                type={passwordViewer ? 'text' : 'password'}
                placeholder="Introduzca su contraseña"
                ref={password}
              />
              <EyeIcon action={handleCheckboxChange} />
            </div>
          </div>
          <button className="login-button" onClick={handleSubmit}>Ingresar</button>
          <p>Si no posee una cuenta para ingresar, <strong> contacte con soporte</strong></p>
        </div>
      </div>
    </div>
  )
}