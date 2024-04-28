/* eslint-disable react/prop-types */
import { HeaderTwo } from "./HeaderTwo"
import '../styles/Styles.css'
import { Table } from "./Table.jsx"

export const MainTwo = ({ datatableIndex, sidebarHandler }) => {
  return (
    <div className="content">
      <HeaderTwo sidebarHandler={sidebarHandler} />
      <main>
        <Table datatableIndex={datatableIndex} />
      </main>
    </div>
  )
}