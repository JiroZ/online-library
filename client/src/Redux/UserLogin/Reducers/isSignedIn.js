const loggedReducer = (state = false, action) => {

    return action.type === 'SIGN_IN';
}
export default loggedReducer

