import jwt from 'jsonwebtoken'
import {LoadAuthToken} from "../Main/ReduxUtils/ReduxPersist";

const IsTokenValid = (token) => {
    let decodedToken = jwt.decode(token, {complete: true});
    let expire = decodedToken.payload.exp
    return expire - Date.now() / 1000 > 0;
}

const CurrentAuthToken = () => {
    if (LoadAuthToken() == null) {
        return null
    } else {
        return LoadAuthToken()
    }
}

export {
    IsTokenValid,
    CurrentAuthToken
}
