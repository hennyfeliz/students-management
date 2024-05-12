/* eslint-disable react/prop-types */
import '../styles/EditModal.css'
import '../styles/Table.css'
import { TableSchemes } from '../utils/TableSchema'

export const EditModal = ({ isEditOpen, closeEditModal, dataEdit, datatableIndex }) => {

  // const items = [{ item1: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }]

  return (
    <div
      className={`modal-edit ${isEditOpen ? 'open-edit' : ''}`}
    >
      <div className="content-information">
        <div className="modal-content">
          <span>Editando de la información | <strong> Schools </strong></span>
          <hr />
          <div className='ed-elements'>
            {
              TableSchemes[datatableIndex].table_headers.map((item, index) => (
                <>
                  <span key={index}>
                    {item}
                  </span>
                  <input type="text" placeholder="Introduzca su usuario..."
                    value={Object.keys(dataEdit)[index + 1]}
                  // value={dataEdit[index]}
                  />
                </>
              ))
            }
          </div>
          <div className="buttons">
            <button className="modal-button gray" onClick={closeEditModal}>Cerrar</button>
            <button className="modal-button green" >Guardar</button>
          </div>
        </div>
      </div>
    </div>
  )
}