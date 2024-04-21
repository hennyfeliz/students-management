import './App.css'
import { BrowserRouter } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { Sidebar } from './components/Sidebar';
import { MainTwo } from './components/MainTwo';
import { useState } from 'react';

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
        {/* <Route path='/' element={<Login />} /> */}
        <Route path='/users/*' element={
          <>
            {
              /*
              <Header />
              */
            }
            <Sidebar datatableIndexHandler={datatableIndexHandler} sidebar={sidebar} />
            <MainTwo datatableIndex={datatableIndex} sidebarHandler={sidebarHandler} />
            {
              /*
              <div className='principal-content'>
                <Main />
              </div>
              */
            }
          </>
        } />
      </Routes>

    </BrowserRouter>
  )
}

export default App
