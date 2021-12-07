const OpenUserModel = () => {
    return {
        type: 'OPEN'
    }
}
const CloseUserModel = () => {
    return {
        type: 'CLOSE'
    }
}
const SignIn = () => {
    return {
        type: 'SIGN_IN'
    }
}

const InitAuthResponse = (authResponse) => {
    return {
        type: 'LOGGED_IN',
        userAuthResponse: authResponse
    }
}

const AdminAuthorities = (isAdmin) => {
    if (isAdmin) {
        return {type: 'ADMIN'}
    } else {
        return {type: 'USER'};
    }
}

export {
    OpenUserModel,
    CloseUserModel,
    SignIn,
    InitAuthResponse,
    AdminAuthorities
}