import {Button, Container, Form, InputGroup} from "react-bootstrap";

import {Formik} from "formik"

import {useDispatch} from "react-redux";
import {AdminAuthorities, CloseUserModel, InitAuthResponse, SignIn} from "../../../../Redux/UserLogin/Actions";

import {LoadAuthToken, SaveAuthResponse, SaveAuthToken} from "../../../ReduxUtils/ReduxPersist.jsx"

import './LoginForm.css'
import * as yup from "yup";
import {APIService} from "../../../../Services/APIService";

const LoginForm = () => {
    const dispatch = useDispatch();

    const handleLoginSubmit = (e) => {
        const body = {
            email: e.email,
            password: e.password
        }

        console.log(body)

        APIService.authUser(e.email, e.password).then(response => {
            console.log(response)
            let isAdmin = false;
            if (response.data.authenticated) {
                if (response.data.user.libraryAuthority[0].role === "ROLE_ADMIN") {
                    isAdmin = true;
                }
                dispatch(InitAuthResponse(response.data))
                dispatch(SignIn())
                dispatch(CloseUserModel())
                dispatch(AdminAuthorities(isAdmin))
                SaveAuthToken(response.data.token)
                SaveAuthResponse(response.data)
            }
        }).catch((err) => {
            console.warn('error during http call', err);
            console.warn('error during http call Response: ', err.response);
        });
        console.log(LoadAuthToken())
        return false;
    };

    const validationSchema = yup.object().shape({
            email: yup
                .string().required('Email is required')
                .matches("^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", "Invalid Email Address"),
            password: yup
                .string().required('Password is required')
        }
    );

    return (
        <Container fluid className="col-2">
            <h1>Login</h1>
            <Formik
                initialValues={{
                    email: '',
                    password: '',
                }}
                onSubmit={(e) => handleLoginSubmit(e)}
                validationSchema={validationSchema}
            >{({
                   handleSubmit,
                   handleChange,
                   values,
                   errors,
               }) => (
                <Form noValidate onSubmit={(e) => handleLoginSubmit(e)}>
                    <Form.Group controlId="blogForm.email">
                        <Form.Label>E-mail</Form.Label>
                        <InputGroup hasValidation>
                            <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                            <Form.Control
                                type="text"
                                placeholder="Enter E-mail"
                                aria-describedby="inputGroupPrepend"
                                name="email"
                                value={values.email}
                                onChange={handleChange}
                                isInvalid={!!errors.email}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.email}
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>

                    <Form.Group controlId="blogForm.password">
                        <Form.Label>Password</Form.Label>
                        <InputGroup hasValidation>
                            <Form.Control
                                type="password"
                                placeholder="Password"
                                aria-describedby="inputGroupPrepend"
                                name="password"
                                value={values.password}
                                onChange={handleChange}
                                isInvalid={!!errors.password}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.password}
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>

                    <br/>

                    <Button variant="primary" type="submit" onClick={(e) => handleSubmit(e)}>
                        Login
                    </Button>
                </Form>
            )}
            </Formik>
        </Container>
    );
}
export default LoginForm;