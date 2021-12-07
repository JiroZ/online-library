import loggedReducer from "./isSignedIn";
import {combineReducers} from "redux";
import modelReducer from "./isModelOpen";
import userReducer from "./User";
import adminCheckReducer from "./isAdmin";

const allReducers = combineReducers({
    isSignedIn: loggedReducer,
    isUserModelOpen: modelReducer,
    getUser: userReducer,
    isAdmin: adminCheckReducer
})

export default allReducers