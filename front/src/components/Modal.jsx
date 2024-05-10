/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import '../styles/Modal.css'
import { TableSchemes } from '../utils/TableSchema';



const Modal = ({ isOpen, closeModal, data, datatableIndex }) => {

  const items = [{ item1: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }, { item2: "123", content: "123" }]
  console.log(data);

  const getPropertyByPath = (obj, path) => {
    const properties = path.split('.');
    return properties.reduce((acc, property) => acc && acc[property], obj);
  };


  return (
    <div
      className={`modal-info ${isOpen ? 'open-info' : ''}`}
    >
      <div className="content-information">
        <div className="modal-content">
          <span>Vista de la información | <strong> {TableSchemes[datatableIndex].title} </strong></span>
          <hr />
          <table className="data-table dt-edit">
            <tbody>
              {TableSchemes[datatableIndex].table_body.map((property, index) => (
                <tr key={index}>
                  <td>{TableSchemes[datatableIndex].table_headers[index]}</td>
                  <td>{property.includes('.') ? getPropertyByPath(data, property) : data[property]}</td>
                </tr>
              ))}
            </tbody>
            {/* <tbody>
              <tr>
                <td>School Name</td>
                <td>
                  {data.schoolName}
                </td>
              </tr>
              <tr>
                <td>Dirección</td>
                <td>
                  {data.address}
                </td>
              </tr>
              <tr>
                <td>Phone Number</td>
                <td>{data.phoneNumber}</td>
              </tr>
            </tbody> */}
          </table>
          <div className="buttons">
            <button className="modal-button gray" onClick={closeModal}>Cerrar</button>
            {/* <button className="modal-button green" >Guardar</button> */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal