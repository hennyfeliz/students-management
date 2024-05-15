import { useState } from 'react'
import '../styles/Notifications.css'


export const Notifications = () => {

  const [notifications, setNotifications] = useState(32);

  const items = [
    {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    }, {
      title: "Alerta",
      message: "Este elemento no puede ser ejecutado debido al código de error: 230219312. Contacte con soporte."
    },
  ]

  return (
    <a href="#" className="profile-notifications">
      <div className="menu-container-notifications">
        <label htmlFor="menu-checkbox-notifications" className="menu-toggler-notifications">
          <a className="notif">
            <i className='bx bx-bell'></i>
            <span className="count">12</span>
          </a>
        </label>
        <input type="checkbox" id="menu-checkbox-notifications" />
        <div className="menu-notifications">
          {
            items.map((item, index) => (
              <a key={index}>
                <i className='bx bx-error'></i>
                <div>
                  <p>{item.title}</p>
                  <p>{item.message}</p>
                </div>
              </a>
            ))
          }
        </div>
      </div>
    </a>
  )
}
