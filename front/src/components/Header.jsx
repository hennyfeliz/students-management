/* eslint-disable react/no-unknown-property */
import '../styles/Header.css'
import { Link } from 'react-router-dom'
import { LogoutIcon } from '../assets/icons/Logout'

const Header = () => {
  return (
    <header>
      <div>
        <div className="menu-container">
          <label htmlFor="menu-checkbox" className="menu-toggler">
            <svg viewBox="0 -0.5 25 25" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" strokeWidth="0"></g><g id="SVGRepo_tracerCarrier" strokeLinecap="round" strokeLinejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M5.5 7.75C5.08579 7.75 4.75 8.08579 4.75 8.5C4.75 8.91421 5.08579 9.25 5.5 9.25V7.75ZM19.5 9.25C19.9142 9.25 20.25 8.91421 20.25 8.5C20.25 8.08579 19.9142 7.75 19.5 7.75V9.25ZM5.5 11.75C5.08579 11.75 4.75 12.0858 4.75 12.5C4.75 12.9142 5.08579 13.25 5.5 13.25V11.75ZM17.5 13.25C17.9142 13.25 18.25 12.9142 18.25 12.5C18.25 12.0858 17.9142 11.75 17.5 11.75V13.25ZM5.5 15.75C5.08579 15.75 4.75 16.0858 4.75 16.5C4.75 16.9142 5.08579 17.25 5.5 17.25V15.75ZM12.5 17.25C12.9142 17.25 13.25 16.9142 13.25 16.5C13.25 16.0858 12.9142 15.75 12.5 15.75V17.25ZM5.5 9.25H19.5V7.75H5.5V9.25ZM5.5 13.25H17.5V11.75H5.5V13.25ZM5.5 17.25H12.5V15.75H5.5V17.25Z" fill="#000000"></path> </g></svg>
          </label>
          <input type="checkbox" id="menu-checkbox" />
          <div className="menu">
            <a href="">🤠 Vequero</a>
            <a href="">😎 Lentes</a>
            <a href="">💀 Esqueleto</a>
            <hr style={{ width: '90%' }} />

            <Link to="/">
              <LogoutIcon />
              Logout
            </Link>
          </div>
        </div>
      </div>
      <div>
        <span>
          <svg width="34px" height="34px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M11.2501 7.06066L8.03039 10.2803L6.96973 9.21967L12.0001 4.18934L17.0304 9.21967L15.9697 10.2803L12.7501 7.06066L12.7501 16.5L11.2501 16.5L11.2501 7.06066Z" fill="#000000"></path> <path d="M18 18L18 19.5L6 19.5L6 18L18 18Z" fill="#000000"></path> </g></svg>
        </span>
        <span>
          <svg width="34px" height="34px" style={{ margin: "0 24px" }} viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M20.6092 8.34165L12.0001 3.64575L3.39093 8.34165L3.75007 9.75007H5.25007V15.7501H4.50007V17.2501H19.5001V15.7501H18.7501V9.75007H20.2501L20.6092 8.34165ZM6.75007 15.7501V9.75007H9.00007V15.7501H6.75007ZM10.5001 15.7501V9.75007H13.5001V15.7501H10.5001ZM15.0001 15.7501V9.75007H17.2501V15.7501H15.0001ZM12.0001 5.35438L17.3088 8.25007H6.69131L12.0001 5.35438ZM3 19.5001H21V18.0001H3V19.5001Z" fill="#000000"></path> </g></svg>
        </span>
        <span>
          <svg width="34px" height="34px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M9 3.75L9.75 3H14.25L15 3.75V5.25H17.25L18 6V9C18 9.66637 17.7103 10.2651 17.25 10.6771V14.25H18.75L19.5 15V18.75L18.75 19.5H17.25V20.25L16.5 21H7.5L6.75 20.25V19.5H5.25L4.5 18.75L4.5 15L5.25 14.25H6.75L6.75 10.6771C6.28969 10.2651 6 9.66638 6 9V6L6.75 5.25H9V3.75ZM10.5 5.25H13.5V4.5H10.5V5.25ZM8.25 11.25V15.75H15.75V11.25H14.25V12.75H12.75V11.25H11.25V12.75H9.75V11.25H8.25ZM9.75 9.75H8.25C7.83579 9.75 7.5 9.41421 7.5 9V6.75H16.5V9C16.5 9.41421 16.1642 9.75 15.75 9.75H14.25V9H12.75V9.75H11.25V9H9.75V9.75ZM15.75 17.25H8.25V19.5H15.75V17.25ZM6.75 18L6.75 15.75H6L6 18H6.75ZM17.25 15.75V18H18V15.75H17.25Z" fill="#000000"></path> </g></svg>
        </span>
      </div>
      <svg className="third-bt-header" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <circle cx="12" cy="6" r="4" stroke="#000000" stroke-width="1.5"></circle> <path d="M19.9975 18C20 17.8358 20 17.669 20 17.5C20 15.0147 16.4183 13 12 13C7.58172 13 4 15.0147 4 17.5C4 19.9853 4 22 12 22C14.231 22 15.8398 21.8433 17 21.5634" stroke="#000000" stroke-width="1.5" stroke-linecap="round"></path> </g></svg>
    </header>
  )
}

export default Header