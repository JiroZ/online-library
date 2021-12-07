import {Container, Form, InputGroup, Button} from "react-bootstrap";
import {Formik} from "formik";
import * as yup from "yup";
import {CloseUserModel} from "../../../../Redux/UserLogin/Actions";
import {useDispatch} from "react-redux";
import {APIService} from "../../../../Services/APIService";

const RegisterForm = () => {
    const dispatch = useDispatch();

    const handleRegisterSubmit = (e) => {
        APIService.registerUser(e.email, e.userName, e.password).then(response => {
            console.log(response)
            console.log(response.data)
        }).catch((err) => {
            console.warn('error during http call', err.response);
        });

        dispatch(CloseUserModel())
        return false
    };

    const validationSchema = yup.object().shape({
            userName: yup
                .string().required('User Name is required')
                .min(3, 'Minimum 3 characters required')
                .max(30, "Maximum 14 characters allowed"),
            email: yup
                .string().required('Email is required')
                .matches("^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+\$", "Invalid Email Address"),
            password: yup
                .string().required('Password is required')
        }
    );


    return (
        <Container fluid className='col-2'>
            <br/>
            <h1>Register</h1>
            <br/>
            <Formik
                initialValues={{
                    userName: '',
                    email: '',
                    password: '',
                }}
                onSubmit={(e) => handleRegisterSubmit(e)}
                validationSchema={validationSchema}
            >{({
                   handleSubmit,
                   handleChange,
                   values,
                   errors,
               }) => (
                <Form noValidate onSubmit={(e) => handleRegisterSubmit(e)}>
                    <Form.Group controlId="blogForm.email">
                        <Form.Label>E-mail</Form.Label>
                        <InputGroup hasValidation>
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

                    <Form.Group controlId="blogForm.userName">
                        <Form.Label>User Name</Form.Label>
                        <InputGroup hasValidation>
                            <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                            <Form.Control
                                type="text"
                                placeholder="Enter User Name"
                                aria-describedby="inputGroupPrepend"
                                name="userName"
                                value={values.userName}
                                onChange={handleChange}
                                isInvalid={!!errors.userName}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.userName}
                            </Form.Control.Feedback>
                        </InputGroup>
                    </Form.Group>

                    <Form.Group controlId="blogForm.password">
                        <Form.Label>Password</Form.Label>
                        <InputGroup hasValidation>
                            <Form.Control
                                type="password"
                                placeholder="Enter Password"
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
                        Register
                    </Button>
                </Form>
            )}
            </Formik>
        </Container>
    );
}
export default RegisterForm;