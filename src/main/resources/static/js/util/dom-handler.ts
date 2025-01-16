const getDomElementOrError = function (query: string) {
    const possibleDomElement = document.querySelector(query);
    if (possibleDomElement === null) {
        //missing error hanlder
        const errorMessage = "couldn't find DOM element with query : " + query;
        console.error(errorMessage);
        return null;
    }
    return possibleDomElement!;
}

export { getDomElementOrError }