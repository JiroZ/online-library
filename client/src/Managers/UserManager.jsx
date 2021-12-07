import {CurrentAuthToken, IsTokenValid} from "./JWTManager";
import {RemoveAuthResponse, RemoveAuthToken} from "../Main/ReduxUtils/ReduxPersist";

const IsUserAuthValid = () => {
    if(CurrentAuthToken() == null) {
        return false
    }
    else {
        return IsTokenValid(CurrentAuthToken())
    }

}

const LogOutUser = () => {
    RemoveAuthResponse()
    RemoveAuthToken()
    window.location.reload();
}
export {
    IsUserAuthValid,
    LogOutUser
}