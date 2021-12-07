const adminCheckReducer = (state = false, action) => {
    return action.type === "ADMIN";
}
export default adminCheckReducer