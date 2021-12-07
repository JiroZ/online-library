const userReducer = (state = {authenticated: false, user: {email: "", password: ""}}, action) => {
    if (action.type === 'LOGGED_IN') {
        return action.userAuthResponse;
    } else {
        return state;
    }
}
export default userReducer