/* eslint-disable react/prop-types */

import '../styles/Contrato.css'
import { HeaderTwo } from './HeaderTwo'


const Contrato = ({ sidebarHandler }) => {
  return (
    <div className="content">
      <HeaderTwo sidebarHandler={sidebarHandler} />
      <main>
        <span>Contratos</span>
      </main>
    </div>
  )
}

export default Contrato