/* eslint-disable react/prop-types */
import { useState } from 'react'
import '../styles/Styles.css'
import { SidebarItems } from '../utils/Helper'
import { BRRDIcon } from '../assets/icons/Icons'
import { Link } from 'react-router-dom'

export const Sidebar = ({ datatableIndexHandler, sidebar }) => {

  const [active, setActive] = useState(0);

  const activeHandler = (index) => {
    datatableIndexHandler(index)
    setActive(index)
  }

  return (
    <div className={sidebar ? "sidebar close" : "sidebar"}>
      <a href="#" className="logo">
        <div className="logo-name">
          <BRRDIcon hide={!sidebar} />
          {
            !sidebar ?
              <>
                <span>an</span>reservas
              </>
              :
              ''
          }
        </div>
      </a>
      <ul className="side-menu">
        {
          SidebarItems.map((item, index) => (
            <li key={index} className={active === index ? 'active normal-side-button' : 'normal-side-button'} onClick={() => activeHandler(index)}>
              {/* <a href='#'> */}
              <Link to={item.link}>
                <i className={item.icon}></i>
                {item.text}
                {/* </a> */}
              </Link>
            </li>
          ))
        }
        {/* {
          SidebarItems.map((item, index) => (
            <li key={index} className={active === index ? 'active normal-side-button' : 'normal-side-button'} onClick={() => activeHandler(index)}>
              <a href='#'>
                <i className={item.icon}></i>
                {item.text}
              </a>
            </li>
          ))
        } */}
      </ul>
      <ul className="side-menu logout-side-menu-button">
        <li>
          {/* <a href="#" className="logout"> */}
          <Link to="/">
            <i className='bx bx-log-out-circle'></i>
            Logout
          </Link>
          {/* </a> */}
        </li>
      </ul>
    </div>
  )
}