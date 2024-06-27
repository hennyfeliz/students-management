/* eslint-disable no-prototype-builtins */
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
import * as XLSX from 'xlsx';
import Cookies from 'js-cookie';
// import { useCookies } from 'react-cookie';

export const Table = ({ datatableIndex }) => {
  const [data, setData] = useState([]);
  const [numPage, setNumPage] = useState(1);
  const [numRecordsPage, setNumRecordsPage] = useState(10);
  const [totalElements, setTotalElements] = useState(0);
  const [sort, setSort] = useState("");
  const [filters, setFilters] = useState({});

  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedEditModalData, setSelectedEditModalData] = useState({});

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedModalData, setSelectedModalData] = useState({});
  const [allChecked, setAllChecked] = useState(false);
  const [collection, setCollection] = useState([]);

  useEffect(() => {
    getData({
      path: TableSchemes[datatableIndex].path,
      sort,
      index: numPage,
      size: numRecordsPage,
      filters,
      headers: {
        "Content-Type": "application/json",
        "Authorization": Cookies.get('token'),
      },
    }).then((data) => {
      setData(data.data);
      setTotalElements(data.totalElements);
      setCollection([]);
    });
  }, [datatableIndex, numPage, numRecordsPage, sort, filters]);

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

  const sortHandler = (sortKey) => {
    setSort((prevSort) => (prevSort === sortKey ? "" : sortKey));
  };

  const handleFilterChange = (filterKey, value) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [filterKey]: value,
    }));
  };

  const openModal = (element) => {
    setSelectedModalData(element);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const openEditModal = (element) => {
    setSelectedEditModalData(element);
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setIsEditModalOpen(false);
  };

  const actualPageHandlerMenus = () => {
    if (numPage > 1) {
      setNumPage(numPage - 1);
    }
  };

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

  const dateFormatter = (date) => {
    return new Date(date).toLocaleString('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
      second: 'numeric',
    });
  };

  const getPropertyValue = (obj, path) => {
    const properties = path.split('.');
    let value = obj;
    for (const prop of properties) {
      if (value && value.hasOwnProperty(prop)) {
        value = value[prop];
      } else {
        value = null;
        break;
      }
    }
    return value;
  };

  const downloadExcel = () => {
    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Data');
    XLSX.writeFile(workbook, 'data.xlsx');
  };

  return (
    <>
      {/* <div className='table-header'>
        <h2>{TableSchemes[datatableIndex].title}</h2>
        <div className='keyword-input-text input-large'>
          <MagnifierIcon />
          <input type="text" placeholder='Keyword Search' />
        </div>
        <div className='export-csv-button' onClick={downloadExcel}>
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
                onClick={handleTheadCheckboxChange}
              ></i>
            </th>
            {TableSchemes[datatableIndex].table_headers.map((item, index) => (
              <th key={index}>
                {item}
                <SortingArrow onClick={() => sortHandler(item.toLowerCase())} />
                <FilterInput
                  filterKey={TableSchemes[datatableIndex].table_body[index]}
                  onFilterChange={handleFilterChange}
                />
              </th>
            ))}
            <th>
              ST
              <SortingArrow />
              <FilterInput filterKey="status" onFilterChange={handleFilterChange} />
            </th>
            <th>TRH<SortingArrow /></th>
            <th>EDT<SortingArrow /></th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index} className="elemento-tabla">
              <td>
                <i
                  className={`bx bx${collection.includes(item.id) ? 's' : ''}-check-square`}
                  onClick={(event) => handleCheckboxChange(event, item.id)}
                ></i>
              </td>
              {TableSchemes[datatableIndex].table_body.map((property, index) => (
                <td key={index} onClick={() => openModal(item)}>
                  {getPropertyValue(item, property)}
                </td>
              ))}
              <td><span className="status active">Activo</span></td>
              <td>
                <i className="bx bx-trash items-icon"></i>
              </td>
              <td>
                <i
                  className="bx bx-edit-alt items-icon"
                  onClick={() => openEditModal(item)}
                ></i>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="paginagor-container pag-2">
        <div>
          <span>
            <i className="bx bx-dock-bottom bx-xs"></i>
            <p>Ver seleccionado</p>
          </span>
          <span>
            <i className="bx bx-trash bx-xs"></i>
            <p>Eliminar seleccionados</p>
          </span>
          <span onClick={downloadExcel}>
            <i className="bx bx-printer bx-xs"></i>
            <p>Guardar Excel</p>
          </span>
        </div>
        <div className="paginagor-container">
          <button>
            <LeftLinedArrow />
          </button>
          <button onClick={actualPageHandlerMenus}>
            <LeftNormalArrow />
          </button>
          <button>{numPage}</button>
          <button onClick={actualPageHandlerPlus}>
            <RightNormalArrow />
          </button>
          <button>
            <RightLinedArrow />
          </button>
        </div>
        <div>
          {`Mostrando los registros de la página ${numPage} de ${Math.ceil(totalElements / numRecordsPage)} páginas totales (${totalElements} registros totales)`}
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
  );
};