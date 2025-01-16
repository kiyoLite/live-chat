const httpHanlder = async function (response: Response, errorHanlder?: () => void) {
    if (response.ok)
        return await response.json()
    if (errorHanlder !== undefined)
        errorHanlder();
    return null;
}
export { httpHanlder }