/* eslint-disable react/prop-types */
import { useState, useEffect } from 'react';
import '../styles/EditModal.css'
import '../styles/Table.css'
import { TableSchemes } from '../utils/TableSchema'

export const EditModal = ({ isEditOpen, closeEditModal, dataEdit, datatableIndex }) => {
  const [formData, setFormData] = useState(dataEdit);

  useEffect(() => {
    setFormData(dataEdit);
  }, [dataEdit]);

  const handleChange = (e, key) => {
    setFormData({
      ...formData,
      [key]: e.target.value,
    });
  };

  const handleSave = () => {
    const path = TableSchemes[datatableIndex].path;
    fetch(`http://localhost:8080/${path}/${formData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error updating data');
        }
        return response.json();
      })
      .then(data => {
        console.log('Data updated successfully:', data);
        closeEditModal();
        // Optionally, you can refresh the table data here.
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  return (
    <div className={`modal-edit ${isEditOpen ? 'open-edit' : ''}`}>
      <div className="content-information">
        <div className="modal-content">
          <span>Editando de la información | <strong>{TableSchemes[datatableIndex].title}</strong></span>
          <hr />
          <div className='ed-elements'>
            {
              TableSchemes[datatableIndex].table_headers.map((item, index) => (
                <div key={index}>
                  <span>{item}: </span>
                  <input
                    type="text"
                    placeholder="Introduzca su usuario..."
                    value={formData[TableSchemes[datatableIndex].table_body[index]] || ''}
                    onChange={(e) => handleChange(e, TableSchemes[datatableIndex].table_body[index])}
                  />
                </div>
              ))
            }
          </div>
          <div className="buttons">
            <button className="modal-button gray" onClick={closeEditModal}>Cerrar</button>
            <button className="modal-button green" onClick={handleSave}>Guardar</button>
          </div>
        </div>
      </div>
    </div>
  );
};