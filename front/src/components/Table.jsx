/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import { useState, useEffect } from 'react'
import '../styles/Table.css'
import { getData } from '../fetch'
import { TrashIcon } from '../assets/icons/TrashIcon'
import { EditIcon } from '../assets/icons/EditIcon'
import { LeftLinedArrow } from '../assets/icons/LeftLinedArrow'
import { LeftNormalArrow } from '../assets/icons/LeftNormalArrow'
import { RightNormalArrow } from '../assets/icons/RightNormalArrow'
import { RightLinedArrow } from '../assets/icons/RightLinedArrow'
import { ExportCSVIcon, MagnifierIcon, SortingArrow } from '../assets/icons/Icons'
import { FilterInput } from './FilterInput'
import Modal from './Modal'
import { EditModal } from './EditModal'
import { TableSchemes } from '../utils/TableSchema'

export const Table = ({ datatableIndex }) => {
  const [data, setData] = useState([])
  const [numPage, setNumPage] = useState(1);
  const [numRecordsPage, setNumRecordsPage] = useState(10)
  const [totalElements, setTotalElements] = useState(0)

  const [actualPage, setActualPage] = useState(1)

  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedEditModalData, setSelectedEditModalData] = useState({});

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedModalData, setSelectedModalData] = useState({});
  const [sort, setSort] = useState("")

  const [allChecked, setAllChecked] = useState(false);
  const [collection, setCollection] = useState([]);

  const handleCheckboxChange = (event, itemId) => {
    if (collection.includes(itemId)) {
      setCollection(collection.filter((id) => id !== itemId));
    } else {
      setCollection([...collection, itemId]);
    }
  };

  const handleTheadCheckboxChange = () => {
    if (!allChecked) {
      setCollection(data.map((item) => item.id));
      setAllChecked(!allChecked);
    } else {
      setCollection([]);
      setAllChecked(!allChecked);
    }
  };

  const sortHandler = (_sort) => {
    if (TableSchemes[datatableIndex].table_body[_sort] != sort) {
      setSort(TableSchemes[datatableIndex].table_body[_sort])
    } else {
      setSort("")
    }
  }

  const openModal = (element) => {
    setSelectedModalData(element);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const openEditModal = (element) => {
    console.log("edit modal")
    setSelectedEditModalData(element);
    setIsEditModalOpen(true);
  }

  const closeEditModal = () => {
    setIsEditModalOpen(false);
  }

  const actualPageHandlerMenus = () => {
    if (numPage > 1) {
      setNumPage(numPage - 1);
    }
  }

  const actualPageHandlerPlus = () => {
    if (numPage < totalElements / numRecordsPage) {
      setNumPage(numPage + 1);
    }
  };

  const actualNumRecordsPageHandler = (event) => {
    const value = parseInt(event.target.value);
    if (!isNaN(value) && value > 0) {
      setNumRecordsPage(value);
    }
  };

  useEffect(() => {
    getData({ path: TableSchemes[datatableIndex].path, sort: sort, index: numPage, size: numRecordsPage })
      .then((data) => {
        setData(data.data)
        setTotalElements(data.totalElements)
      })
    console.log(data)
    setCollection([])
  }, [datatableIndex, numPage, numRecordsPage, sort])

  const dateFormatter = (date) => {
    return new Date(date).toLocaleString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric' });

  }

  const getPropertyValue = (obj, path) => {
    const properties = path.split('.');
    let value = obj;
    for (const prop of properties) {
      // eslint-disable-next-line no-prototype-builtins
      if (value && value.hasOwnProperty(prop)) {
        value = value[prop];
      } else {
        value = null;
        break;
      }
    }
    return value;
  };


  return (
    <>
      {/* <div className='table-header'>
        <h2>
          {TableSchemes[datatableIndex].title}
        </h2>
        <div className='keyword-input-text input-large' >
          <MagnifierIcon />
          <input type="text" placeholder='Keyword Search' />
        </div>
        <div className='export-csv-button'>
          Export
          <ExportCSVIcon />
        </div>
      </div> */}
      <table className="data-table">
        <thead>
          <tr>
            <th>
              <i
                className={`bx bx${allChecked ? 's' : ''}-check-square`}
                onClick={handleTheadCheckboxChange}>
              </i>
              {/* <div className="cntr">
              <input checked="true" type="checkbox" id="cbx" className="hidden-xs-up" />
              <label htmlFor="cbx" className="cbx" onClick={handleTheadCheckboxChange}></label>
            </div> */}
              {/* <input type="checkbox" id="thead-checkbox" */}
              {/* // onChange={handleTheadCheckboxChange} /> */}
            </th>
            {
              TableSchemes[datatableIndex].table_headers.map((item, index) => (
                <th key={index}
                  onClick={() => sortHandler(index)}
                >
                  {item}
                  <SortingArrow />
                  <FilterInput />
                </th>

              ))
            }
            <th>
              ST
              <SortingArrow />
              <FilterInput />
            </th>
            <th>
              TRH
              <SortingArrow />
            </th>
            <th>
              EDT
              <SortingArrow />
            </th>
          </tr>
        </thead>
        <tbody>
          {
            data.map((item, index) => (
              <tr key={index} className='elemento-tabla'

              >
                <td>
                  <i
                    className={`bx bx${collection.includes(item.id) ? 's' : ''}-check-square`}
                    onClick={(event) => handleCheckboxChange(event, item.id)}>
                  </i>
                  {/* <div className="cntr" onClick={(event) => handleCheckboxChange(event, item.id)}>
                    <input checked={collection.includes(item.id)} type="checkbox" id="cbx" className="hidden-xs-up" onChange={(event) => handleCheckboxChange(event, item.id)} />
                    <label htmlFor="cbx" className="cbx" onClick={(event) => handleCheckboxChange(event, item.id)}></label> */}
                  {/* </div> */}
                  {/* <div className="cntr">
                    <input
                      // checked="" 
                      type="checkbox" id="cbx"
                      // type="checkbox"
                      // id={`checkbox-${item.id}`}
                      checked={collection.includes(item.id)}
                      className="hidden-xs-up" />
                    <label
                      htmlFor="cbx"
                      className="cbx"
                      onChange={(event) => handleCheckboxChange(event, item.id)}
                    ></label>
                  </div> */}
                  {/* <input 
                    type="checkbox" 
                    id={`checkbox-${item.id}`}
                    onChange={(event) => handleCheckboxChange(event, item.id)}
                    checked={collection.includes(item.id)} /> */}
                </td>
                {TableSchemes[datatableIndex].table_body.map((property, index) => (
                  <td key={index} onClick={() => openModal(item)}>
                    {getPropertyValue(item, property)}
                  </td>
                ))}
                {/* {
                  TableSchemes[datatableIndex].table_body.map((_item, _index) => (
                    <td onClick={() =>
                      openModal(item)} key={_index}>{item[_item]}</td>
                  ))
                } */}
                {/* <td>{dateFormatter(item.date)}</td> */}
                <td><span className="status active">Activo</span></td>
                <td>
                  <i className='bx bx-trash items-icon'></i>
                  {/* {<TrashIcon width='30px' height='30px' />} */}
                </td>
                <td>
                  <i className='bx bx-edit-alt items-icon' onClick={() => openEditModal(item)}></i>

                  {/* {<EditIcon action={openEditModal} item={item} datatableIndex={datatableIndex} />} */}
                </td>
              </tr>
            ))}
        </tbody>
      </table >
      <div className='paginagor-container pag-2' >
        <div>
          <span>
            <i className='bx bx-dock-bottom bx-xs'></i>
            <p>
              Ver seleccionado
            </p>
          </span>
          <span>
            <i className='bx bx-trash bx-xs'></i>
            <p>
              Eliminar seleccionados
            </p>
          </span>
          <span>
            <i className='bx bx-printer bx-xs'></i>
            <p>Guardar Excel</p>
          </span>
        </div>
        <div className="paginagor-container">
          <button>
            <LeftLinedArrow />
          </button>
          <button onClick={() => actualPageHandlerMenus()} >
            <LeftNormalArrow />
          </button>
          {/* <button className={numPage <= 1 ? "inactive-button" : "inactive-button"}>
            {numPage == 0 ? numPage - 1 : ""}
          </button> */}
          <button>
            {numPage}
          </button>
          {/* <button>
            {numPage + 1}
          </button> */}
          <button onClick={() => actualPageHandlerPlus()} >
            <RightNormalArrow />
          </button>
          <button>
            <RightLinedArrow />
          </button>
        </div>
        <div>
          {
            `Mostrando los registros de la página ${numPage} de ${totalElements / numRecordsPage} páginas totales (${totalElements} registros totales)`
          }
        </div>
      </div>
      <Modal
        isOpen={isModalOpen}
        closeModal={closeModal}
        data={selectedModalData}
        datatableIndex={datatableIndex}
      />
      <EditModal
        isEditOpen={isEditModalOpen}
        closeEditModal={closeEditModal}
        dataEdit={selectedEditModalData}
        datatableIndex={datatableIndex}
      />
    </>
  )
}
