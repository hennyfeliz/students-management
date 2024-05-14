/* eslint-disable react/prop-types */
import { useEffect, useState } from 'react'
import '../styles/Styles.css'
import '../styles/Header.css'
import { LogoutIconn } from '../assets/icons/Icons';
import { Notifications } from './Notifications';

export const HeaderTwo = ({ sidebarHandler }) => {

  const [dark, setDark] = useState(false);

  useEffect(() => {
    if (dark) {
      document.body.classList.add('dark');
    } else {
      document.body.classList.remove('dark');
    }
  }, [dark]);

  const darkHandler = () => {
    setDark(!dark);
  }

  return (
    <nav>
      <i className='bx bx-menu' onClick={sidebarHandler}></i>
      <form action="#">
        <div className="form-input">
          <input type="search" placeholder="Search..." />
          <button className="search-btn" type="submit"><i className='bx bx-search'></i></button>
        </div>
      </form>
      <input type="checkbox" id="theme-toggle" hidden onClick={darkHandler} />
      <label htmlFor="theme-toggle" className="theme-toggle"></label>
      <Notifications />
      {/* <a href="#" className="notif">
        <i className='bx bx-bell'></i>
        <span className="count">12</span>
      </a> */}
      <a href="#" className="profile">
        <div className="menu-container">
          <label htmlFor="menu-checkbox" className="menu-toggler">
            <i className='bx bx-user-circle'></i>
          </label>
          <input type="checkbox" id="menu-checkbox" />
          <div className="menu">
            <a href="">
              <i className='bx bx-objects-vertical-center'></i>
              <p>Actividad</p>
            </a>
            <a href="">
              <i className='bx bx-building-house'></i>
              <p>
                Organización
              </p>
            </a>
            <a href="">
              <i className='bx bx-cog'></i>
              <p>
                Configuración
              </p>
            </a>
            <hr style={{ width: '90%' }} />
            <a href="">
              <i className='bx bx-log-out-circle'></i>
              {/* <LogoutIconn /> */}
              {` Cerrar sesión`}
            </a>
            {
              /*
            <Link to="/">
              <LogoutIcon />
              Logout
            </Link>
              */
            }
          </div>
        </div>
      </a>
    </nav>
  )
}