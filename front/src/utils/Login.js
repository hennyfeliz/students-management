/* prettier-ignore-file */
const loginFetch = ({ username, password }) => {
  return fetch("http://localhost:8080/secured/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username: username,
      password: password,
    }),
  });
};

export default loginFetch;
