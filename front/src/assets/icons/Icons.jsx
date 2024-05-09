/* eslint-disable react/prop-types */
/* eslint-disable react/no-unknown-property */
import '../../styles/Table.css'

export const MagnifierIcon = () => {
  return (
    <svg width="24px" height="24px" viewBox="0 0 32 32" enable-background="new 0 0 32 32" id="Editable-line" version="1.1" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#6b7280"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="14" cy="14" fill="none" id="XMLID_42_" r="9" stroke="#6b7280" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="2"></circle><line fill="none" id="XMLID_44_" stroke="#6b7280" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="2" x1="27" x2="20.366" y1="27" y2="20.366"></line></g></svg>
  )
}

export const DownSortArrow = ({ width = "18px", height = "18px", action }) => {
  return (
    <svg onClick={action} width={width} height={height} viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
      <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
      <g id="SVGRepo_iconCarrier">
        <path d="M7.00012 3V21M7.00012 21L3.00012 17M7.00012 21L11.0001 17M14.0001 21H21.0001M14.0001 15H19.0001M14.0001 9H17.0001M14.0001 3H15.0001" stroke="#6b7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        </path> </g>
    </svg>
  )
}

export const UpSortArrow = ({ width = "18px", height = "18px", action }) => {
  return (
    <svg className="table-header-icon" onClick={action} width={width} height={height} viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M7 3V21M7 3L11 7M7 3L3 7M14 3H15M14 9H17M14 15H19M14 21H21" stroke="#6b7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
  )
}

export const SortingArrow = ({ width = "18px", height = "18px", action }) => {
  return (
    <svg className="table-header-icon" onClick={action} width={width} height={height} viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <g id="SVGRepo_bgCarrier" stroke-width="0">

      </g>
      <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round">
      </g>
      <g id="SVGRepo_iconCarrier">
        <path d="M7 4V20M7 20L3 16M7 20L11 16M17 4V20M17 4L21 8M17 4L13 8" stroke="#6b7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        </path>
      </g>
    </svg>
  )
}

export const ExportCSVIcon = ({ width = "18px", height = "18px", action }) => {
  return (
    <svg onClick={action} width={width} height={height} viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" fill="none" stroke="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><polyline points="48 24 32 8 16 24"></polyline><line x1="56" y1="56" x2="8" y2="56"></line><line x1="32" y1="48" x2="32" y2="8"></line></g></svg>
  )
}


export const BRRDIcon = ({ hide }) => {
  return (
    <svg height="24px" style={{ marginLeft: hide ? "30px" : "14px" }} viewBox="28.65 381.72 301.69 287.65" width="24px" xmlns="http://www.w3.org/2000/svg"><text fill="#254c74" font-family="'FreightSansProSemibold-Regular'" font-size="142.137" transform="matrix(1.0524 0 0 1 356.882 592.184)">banreservas</text><text fill="#254c74" font-family="'FreightSansProMedium-Regular'" font-size="69.398" transform="translate(360.486 678.32)">El banco de los dominicanos</text><path d="m275.22 515.83c33.13 11.33 52.75 33.12 55.12 69.55-37.01-42.42-85.87-61.14-138.25-73.97 5.54-.67 10.77-1.09 15.93-1.97 13.88-2.37 27.21-6.25 38.35-15.44 11.59-9.57 15.46-22.02 13.21-36.55-2.24-14.46-11.41-22.97-24.65-27.64-8.87-3.13-18.08-4-27.47-3.78-9.8.23-19.6.07-29.41.09h-3.43v81.52c-17.62-3.18-34.81-6.28-52.35-9.45v-115.4c1.04-.1 2-.27 2.95-.27 37.72-.04 75.45-.8 113.14.15 29.17.74 53.51 12.64 68.73 38.62 19.39 33.12 4.52 77.9-30.44 93.76-.44.2-.84.46-1.43.78z" fill="#274d72" /><path d="m28.65 539.26c13.03-1.91 26.04-4.01 39.11-5.68 26.32-3.36 52.77-4.81 79.3-3.98 35.02 1.09 69.51 5.56 102.73 17.35 18.78 6.66 35.92 16.17 49.85 30.66 13.7 14.25 21.14 30.87 17.55 51.14-.37 2.11-1.03 4.21-1.86 6.18-1.17 2.78-3.27 3.33-5.53 1.31-1.51-1.35-2.56-3.22-3.76-4.9-9.75-13.75-22.26-24.6-36.07-34-31.21-21.26-65.86-34.8-102.52-42.49-100.72-21.13-138.8-15.59-138.8-15.59z" fill="#02aef2" /><path d="m81.07 583.63c5.16-.47 9.58-.95 14.01-1.27 26.36-1.93 52.74-3 79.14-.98 28.41 2.18 55.99 7.73 81.11 21.88 36.74 23.78 33.26 57.81 31.99 61.46-1.45 4.17-4.87 4.65-7.76 1.66-15.64-16.21-34.29-28.24-54.02-38.73-31.91-16.97-65.87-28.25-101.1-35.82-14.1-3.04-28.35-5.38-43.37-8.2z" fill="#f79220" /></svg>
  )
}


export const EyeIcon = ({ action }) => {
  return (
    <svg onClick={action} className='eye-icon' width="14px" height="14px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#6b7280"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M3 14C3 9.02944 7.02944 5 12 5C16.9706 5 21 9.02944 21 14M17 14C17 16.7614 14.7614 19 12 19C9.23858 19 7 16.7614 7 14C7 11.2386 9.23858 9 12 9C14.7614 9 17 11.2386 17 14Z" stroke="#6b7280" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
  )
}