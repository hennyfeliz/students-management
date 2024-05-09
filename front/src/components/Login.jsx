import { useRef, useState } from 'react';
import '../styles/Login.css'
import { useNavigate } from 'react-router-dom';
import { EyeIcon } from '../assets/icons/Icons';


export const Login = () => {

  const navigate = useNavigate();

  const email = useRef()
  const password = useRef()

  const [passwordViewer, setPasswordViewer] = useState(false);

  const handleSubmit = async () => {
    console.log(email.current.value, password.current.value)
    navigate("/users")
    // validateUser(email.current.value, password.current.value)
    //   .then((response) => {
    //     if (true) {
    //       new AWN().success('Usuario verificado de manera correcta.')
    //     } else {
    //       new AWN().alert('Error de credenciales, vuelva a intentar.')
    //     }
    //   })
    //   .catch((err) => {
    //     new AWN().alert('Error de credenciales, vuelva a intentar.')
    //     console.log(err)
    //   })
  }

  const handleCheckboxChange = () => {
    setPasswordViewer(!passwordViewer)
  }

  return (
    <div className='login-component'>
      {/* <div className='login'>
        <input type="text" placeholder='username' />
        <input type="password" placeholder='password' />
      </div> */}
      <div className='login'>
        <div className="login-card">
          <div className='login-icon'>
            <h2>Bienvenido a su app</h2>
            <span>Por favor, introduzca sus datos para entrar</span>
            {/* <svg width="92px" height="92px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" >
              <circle cx="12" cy="9" r="3" stroke="#fff" strokeWidth="1.5" />
              <path d="M17.9691 20C17.81 17.1085 16.9247 15 11.9999 15C7.07521 15 6.18991 17.1085 6.03076 20" stroke="#fff" strokeWidth="1.5" strokeLinecap="round" />
              <path d="M7 3.33782C8.47087 2.48697 10.1786 2 12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12C2 10.1786 2.48697 8.47087 3.33782 7" stroke="#fff" strokeWidth="1.5" strokeLinecap="round" />
            </svg> */}
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
          <span>Si no posee una cuenta para ingresar, <strong> contacte con soporte</strong></span>
        </div>
      </div>
    </div>
  )
}