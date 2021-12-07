import {Container} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {APIService} from "../../Services/APIService";
import './Home.css'
import './Elements/CustomButtons.js'
import {BookActionButton} from "./Elements/CustomButtons";
import {useSelector} from "react-redux";

const HomePage = () => {

    const [homeData, setHomeData] = useState([])
    const [issuedBooks, setIssuedBooks] = useState([])

    const emailId = useSelector(state => state.getUser.user.email)

    useEffect(() => {
        APIService.getAccessibleData()
            .then(response => {
                console.log(response.data)
                setHomeData(response.data)
            }).catch(function (ex) {
            console.log('Response parsing failed. Error: ', ex);
            console.log('Response parsing failed. Error: Server Response', ex.response);
        });

        APIService.getIssuedBooks(emailId)
            .then(response => {
                setIssuedBooks(response.data)
            }).catch(function (ex) {
            console.log('Error while fetching issued books Error: ', ex);
            console.log('Error while fetching issued books: Server Response', ex.response);
        });
    }, [])

    return (
        <>
            {
                <Container className='col-10'>
                    <br/>
                    <h1>Online Library</h1>
                    <br/>
                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Book Name</th>
                            <th scope="col">Book Description</th>
                            <th scope="col">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            homeData.map((row, index) => {
                                return (
                                    <tr key={row.id}>
                                        <th scope="row">{index + 1}</th>
                                        <td>{row.bookTitle}</td>
                                        <td>{row.bookDescription}</td>
                                        <td><BookActionButton bookId={row.id} IssuedBooks={issuedBooks}/></td>
                                    </tr>
                                )
                            })

                        }
                        </tbody>
                    </table>
                </Container>
            }
        </>
    )
}

export {
    HomePage
}