import {useSelector} from "react-redux";
import {Button, Form, FormControl} from "react-bootstrap";
import React from "react";
import axios from "axios";

const SearchBar = () => {
    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)

    const config = {
        headers: {
            'Content-Type': 'application/JSON'
        }
    }

    const handleSearch = (e) => {
        e.preventDefault();
        this.setState({redirect: '/search'})
        const body = {searchString: this.state.searchString, category: this.state.selectedCategory}
        console.log(body)

        axios.post('http://localhost:8082/search', body, config).then(response => {
            this.setState({searchData: response.data})
        }).catch(err => {
            console.warn("Error while fetching searched data : " + err)
        })
    }

    return (
        <>
            {
                isUserAuthenticated ?
                    <Form className="d-flex">
                        <FormControl
                            type="search"
                            placeholder="Search"
                            className="me-2"
                            aria-label="Search"
                            onChange={(e) => {
                                this.setState({searchString: e.target.value})
                            }}
                            onSubmit={(e) => handleSearch(e)}
                        />
                        <Button variant="outline-success" type='submit'
                                onClick={(e) => handleSearch(e)}> Search </Button>
                    </Form> :
                    <></>
            }
        </>
    )
}
export default SearchBar