import {MenuItems} from './Components/MenuItems'
import UserModel from './Models/UserModel'
import './Navbar.css'
import {Navbar, Nav, Container} from 'react-bootstrap';
import React from "react";
import {HandleSearchRedirect} from "../../Pages/SearchRedirect";
import SearchBar from "./SearchBar";

class NavbarMain extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedCategory: 'ALL',
            categories: [],
            searchString: '',
            redirect: null,
            searchData: null,
            createBlogRedirect: null
        }
    }

    render() {

        return (
            <>
                <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant='dark'>
                    <Container>
                        <Navbar.Brand href="/">
                            <img
                                alt=""
                                src="https://www.iconpacks.net/icons/2/free-opened-book-icon-3169-thumb.png"
                                width="30"
                                height="30"
                                className="d-inline-block align-top"
                            />{' '}
                            Online Library
                        </Navbar.Brand>

                        <Navbar.Toggle aria-controls='responsive-navbar-nav'/>
                        <Navbar.Collapse id='responsive-navbar-nav'>
                            {/*<SearchBar/>*/}

                            <Nav>
                                {
                                    MenuItems.map((item, index) => {
                                        return (
                                            <Nav.Link key={index} href={item.url}>{item.title}</Nav.Link>
                                        )
                                    })
                                }

                            </Nav>
                        </Navbar.Collapse>
                        <Navbar.Toggle/>
                    </Container>

                    <Navbar.Text className="justify-content-end">
                        <UserModel/>
                    </Navbar.Text>
                </Navbar>
                <div
                    style={{
                        display: 'flex',
                        justifyContent: 'Center',
                        alignItems: 'Right',
                        height: '10vh'
                    }}
                />

                {
                    (this.state.redirect != null && this.state.searchData != null) ?
                        <HandleSearchRedirect searchString={this.state.searchString}
                                              seachData={this.state.searchData}/> :
                        <></>
                }
            </>
        );
    }
}

export default NavbarMain;