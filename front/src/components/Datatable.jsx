/* eslint-disable no-unused-vars */
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column';
import { getData } from '../fetch';
import { useEffect, useState } from 'react'
import '../App.css'


const Datatable = () => {
  const [schools, setSchools] = useState([]);
  const [page, setPage] = useState(0);

  useEffect(() => {
    getData({ path: "school" })
      .then((data) => setSchools(data.data))

  }, [])

  const onRowEditComplete = () => {

  }

  const textEditor = () => {

  }

  return (

    <div>
      <DataTable className='datatable-styles' value={schools} lazy filterDisplay="row" editMode="row" dataKey="id" onRowEditComplete={onRowEditComplete} paginator
        rows={10} totalRecords={10} onPage={page}
      >
        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }} />
        <Column editor={(options) => textEditor(options)} field="schoolName" header="School Name" sortable filter filterPlaceholder="Search" filterField='schoolName' sortField='schoolName' />
        <Column field="address" header="Address" sortable filter filterPlaceholder="Search" />
        <Column field="phoneNumber" header="Phone Number" sortable filter filterPlaceholder="Search" />
        <Column rowEditor={true} headerStyle={{ width: '10%', minWidth: '8rem' }} bodyStyle={{ textAlign: 'center' }}></Column>
      </DataTable>
    </div>
  )
}

export default Datatable