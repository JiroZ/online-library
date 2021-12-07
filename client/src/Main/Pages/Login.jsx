import {useSelector} from "react-redux";
import LoginForm from "../Elements/Navbar/Login/LoginForm";
import {useState} from "react";
import RegisterForm from "../Elements/Navbar/Register/RegisterForm";
import {Button} from "react-bootstrap";

const LoginPage = () => {
    useSelector(state => state.getUser.authenticated)
    const [isLoginModel, setLoginModel] = useState(true);


    function handleRegister() {
        if (!isLoginModel) {
            console.log("Register");
        } else {
            setLoginModel(false);
        }
    }

    function handleSignIn() {
        if (isLoginModel) {
            console.log("Login");
        } else {
            setLoginModel(true);
        }
    }

    return (
        <>
            <img src='https://www.iconpacks.net/icons/2/free-opened-book-icon-3169-thumb.png' alt='Logo Image'/>
            <div className='formBody'>
                {isLoginModel ? <LoginForm /> : <RegisterForm />}
                {isLoginModel ?
                    <div>
                        <label>Dont have a account yet?</label>
                        <Button variant="contained" onClick={handleRegister}>Register</Button>
                    </div>
                    :
                    <div>
                        <label>Have a account?</label>
                        <Button variant="contained" onClick={handleSignIn}>Login</Button>
                    </div>}
            </div>
        </>
    )
}
export {
    LoginPage
}