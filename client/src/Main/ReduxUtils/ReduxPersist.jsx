const loadState = () => {
    try {
        const serializedState = localStorage.getItem('state');
        if (serializedState === null) {
            return undefined;
        }
        return JSON.parse(serializedState);
    } catch (err) {
        return undefined;
    }
};
const saveState = (state) => {
    try {
        const serializedState = JSON.stringify(state);
        localStorage.setItem('state', serializedState);
    } catch {
        // ignore write errors
    }
};

const SaveAuthToken =  (token) => {
    try {
        localStorage.setItem('token', JSON.stringify(token));
    } catch (err) {
        // ignore write errors
    }
}

const LoadAuthToken = () => {
    try {
        const serializedState = localStorage.getItem('token');
        if (serializedState == null) {
            return null;
        }
        return JSON.parse(serializedState);
    } catch (err) {
        return undefined;
    }
}

const RemoveAuthToken = () => {
    try {
        const serializedState = localStorage.getItem('token');
        if (serializedState === null) {
            return undefined;
        }
        return localStorage.removeItem('token');
    } catch (err) {
        return undefined;
    }
}

const SaveAuthResponse = (response) => {
    try {
        localStorage.setItem('authResponse', JSON.stringify(response));
    } catch (err) {
        // ignore write errors
    }
}

const LoadAuthResponse = () => {
    try {
        const serializedState = localStorage.getItem('authResponse');
        if (serializedState === null) {
            return undefined;
        }
        return JSON.parse(serializedState);
    } catch (err) {
        return undefined;
    }
}

const RemoveAuthResponse = () => {
    try {
        const serializedState = localStorage.getItem('authResponse');
        if (serializedState === null) {
            return undefined;
        }
        return localStorage.removeItem('authResponse');
    } catch (err) {
        return undefined;
    }
}

export {
    loadState,
    saveState,
    SaveAuthToken,
    LoadAuthToken,
    RemoveAuthToken,
    SaveAuthResponse,
    LoadAuthResponse,
    RemoveAuthResponse
}