export const getData = ({
  path,
  index = 1,
  size = 10,
  sort = "",
  method = "GET",
  headers = { "Content-Type": "application/json" },
}) => {
  const url = `http://localhost:8080/${path}?page=${
    index - 1
  }&size=${size}&sort=${sort}`;

  return fetch(url, {
    method: method,
    headers: headers,
  })
    .then((response) => {
      return response.json();
    })
    .catch((error) => {
      console.error("Error al intentar obtener los resultados: ", error);
      throw error;
    });
};
