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
