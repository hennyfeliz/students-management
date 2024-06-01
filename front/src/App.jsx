import './App.css'
import { BrowserRouter } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { Sidebar } from './components/Sidebar';
import { MainTwo } from './components/MainTwo';
import { useState } from 'react';
import { Login } from './components/Login';
import Contrato from './components/Contrato';


function App() {

  const [sidebar, setSidebar] = useState(false);
  const [datatableIndex, setDatatableIndex] = useState(0);

  const datatableIndexHandler = (index) => {
    setDatatableIndex(index)
  }

  const sidebarHandler = () => {
    setSidebar(!sidebar)
  }

  return (
    // <Datatable />
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/users/*' element={
          <>
            <Sidebar datatableIndexHandler={datatableIndexHandler} sidebar={sidebar} />
            <MainTwo datatableIndex={datatableIndex} sidebarHandler={sidebarHandler} />
          </>
        } />
        <Route path='/contrato/*' element={
          <>
            <Sidebar datatableIndexHandler={datatableIndexHandler} sidebar={sidebar} />
            <Contrato sidebarHandler={sidebarHandler} />
          </>
        } />
      </Routes>

    </BrowserRouter>
  )
}

export default App
