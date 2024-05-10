import '../styles/EditModal.css'
import '../styles/Table.css'

export const EditModal = ({ isEditOpen, closeEditModal, dataEdit, datatableIndex }) => {

  const items = [{ item1: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }]

  return (
    <div
      className={`modal-edit ${isEditOpen ? 'open-edit' : ''}`}
    >
      <div className="content-information">
        <div className="modal-content">
          <span>Editando de la información | <strong> Schools </strong></span>
          <hr />
          <table className="data-table dt-edit">
            <tbody>
              {
                items.map((item, index) => (
                  <tr key={index}>
                    <td>asd</td>
                    <td>
                      {/* {item.content} */}
                      <input type="text" name="" id="" className='edit-modal-input' />
                    </td>
                  </tr>
                ))
              }
            </tbody>
          </table>
          <div className="buttons">
            <button className="modal-button gray" onClick={closeEditModal}>Cerrar</button>
            <button className="modal-button green" >Guardar</button>
          </div>
        </div>
      </div>
    </div>
  )
}