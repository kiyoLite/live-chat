const apiCallContactsFromUser = function (username: string) {
    const url = `http://localhost:8080/api/contact/${username}`;
    return fetch(url)

}

const apiCallContactAdditon = function (username: string, contactName: string) {
    const url = `http://localhost:8080/api/contact/${username}`;
    const httpMethod = "POST";
    const bodyRequest = JSON.stringify({ "contactName": contactName })
    return fetch(url, { method: httpMethod, headers: { "Content-type": "application/json" }, body: bodyRequest });
}

export { apiCallContactsFromUser, apiCallContactAdditon }