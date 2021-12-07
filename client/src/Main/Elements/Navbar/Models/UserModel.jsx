import {useSelector} from "react-redux";

import {Nav} from 'react-bootstrap';
import {LogOutUser} from "../../../../Managers/UserManager";

const UserModel = () => {
    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)
    const isAdmin = useSelector(state => state.isAdmin)
    const userEmail = useSelector(state => state.getUser.user.email)

    return (
        <>
            {isUserAuthenticated ?
                <Nav>
                    <label>Welcome {userEmail}</label>
                    {
                        isAdmin ?
                            <Nav.Link href="/admin">Admin</Nav.Link>
                            : <> </>
                    }
                    {
                        <Nav.Link onClick={(e) => LogOutUser()}>Log Out</Nav.Link>
                    }
                </Nav>
                : <></>}
        </>
    );
}

export default UserModel