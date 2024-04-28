/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import '../styles/Modal.css'


const Modal = ({ isOpen, closeModal, data }) => {

  return (
    <div
      className={`modal ${isOpen ? 'open' : ''}`}
    >
      <div className="product-content-information">
        <div className="modal-content">
          <div className="buttons">
            <button className="modal-button gray" onClick={closeModal}>Cerrar</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal