import NavbarMain from './NavbarMain.jsx'
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {MenuItems} from "./Components/MenuItems";
import AdminPanel from "./Components/AdminCompoenent.jsx";
import {useSelector} from "react-redux";
import {LoginPage} from "../../Pages/Login";

const NavbarRouter = () => {
    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)

    if (isUserAuthenticated) {
        return (
            <>
                <Router>
                    <NavbarMain/>
                    <Switch>
                        {
                            MenuItems.map((item, index) => {
                                return (
                                    <Route key={index} path={item.url} exact component={item.component}/>
                                )
                            })
                        }
                        <Route path="/admin" component={AdminPanel}/>
                    </Switch>
                </Router>
            </>
        )
    } else {
        return (
            <>
                <LoginPage/>
            </>
        )
    }
}
export default NavbarRouter