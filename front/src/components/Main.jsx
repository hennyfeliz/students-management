import { Routes, Route } from "react-router-dom"
import '../styles/Main.css'
// import Datatable from "./Datatable"
import { Table } from "./Table"

export const Main = () => {
  return (
    <main >
      <Routes>
        {/* <Route path='/school' element={<Datatable />} />
        <Route path='/school' element={<Datatable />} /> */}
        <Route path='/' element={<Table />} />
        <Route path='/' element={<Table />} />
      </Routes>
    </main>
  )
}