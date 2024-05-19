/* eslint-disable react/prop-types */
import { MagnifierIcon } from "../assets/icons/Icons"

export const FilterInput = ({ filterKey, onFilterChange }) => {
  const handleChange = (event) => {
    onFilterChange(filterKey, event.target.value);
  };

  return (
    <div className="keyword-input-text input-short">
      <MagnifierIcon />
      <input
        type="text"
        placeholder="Keyword Search"
        onChange={handleChange}
      />
    </div>
  );
};