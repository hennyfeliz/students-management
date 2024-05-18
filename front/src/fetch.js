export const getData = ({
  path,
  index = 1,
  size = 10,
  sort = "",
  filters = {},
  method = "GET",
  headers = { "Content-Type": "application/json" },
}) => {
  const queryParams = new URLSearchParams({
    page: index - 1,
    size,
    sort,
    ...filters,
  });

  const url = `http://localhost:8080/${path}?${queryParams.toString()}`;

  return fetch(url, {
    method: method,
    headers: headers,
  })
    .then((response) => response.json())
    .catch((error) => {
      console.error("Error fetching data: ", error);
      throw error;
    });
};
