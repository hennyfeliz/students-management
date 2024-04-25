import { MagnifierIcon } from "../assets/icons/Icons"

export const FilterInput = () => {
  return (
    <div className='keyword-input-text input-short'>
      <MagnifierIcon />
      <input type="text" placeholder='Keyword Search' />
    </div>
  )
}