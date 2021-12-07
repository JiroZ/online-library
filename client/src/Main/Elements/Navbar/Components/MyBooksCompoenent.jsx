import {withRouter} from "react-router-dom";
import {Button, Container} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {APIService} from "../../../../Services/APIService";
import {useSelector} from "react-redux";

const MyBooks = () => {
    const [issuedBooks, setIssuedBooks] = useState([])
    const [myBooks, setMyBooks] = useState([])

    const emailId = useSelector(state => state.getUser.user.email)

    const [mountData, setMountData] = useState(false)

    function handleDownload(e, id) {
        e.preventDefault()

        APIService.getBook(id).then(response => {
            let binaryString = window.atob(response.data.bookFileByte);
            let binaryLen = binaryString.length;
            let bytes = new Uint8Array(binaryLen);
            for (let i = 0; i < binaryLen; i++) {
                bytes[i] = binaryString.charCodeAt(i);
            }
            let blob = new Blob([bytes], {type: "application/pdf"});
            let url = URL.createObjectURL(blob);
            window.open(url);
        }).catch(err => {
            console.log("Error while downloading the book", err)
            console.log("Error while downloading the book server response: ", err.response)
        })
    }

    useEffect(() => {
        APIService.getIssuedBooks(emailId)
            .then(response => {
                setIssuedBooks(response.data)

                let bookPromises = []
                for (let issuedBook of issuedBooks) {
                    bookPromises.push(APIService.getBookData(issuedBook.bookId))
                }

                Promise.all(bookPromises).then(books => {
                    console.log('books', books)
                    setMyBooks(books)
                    setMountData(true);
                }).catch(err => {
                    console.log(err)
                    console.log(err.response)
                })
            }).catch(function (ex) {
            console.log('Error while fetching issued books Error: ', ex);
            console.log('Error while fetching issued books: Server Response', ex.response);
        });

    }, [mountData])

    function calculateDaysLeft(index) {
        let book = issuedBooks[index]

        const oneDay = 24 * 60 * 60 * 1000;
        const issueDate = new Date(book.issueDate)
        const expDate = new Date(book.expDate)

        const diffDays = Math.round(Math.abs((issueDate - expDate) / oneDay));

        return (<strong>{diffDays} Days Left</strong>)
    }

    return (
        <>
            <>
                {
                    mountData ?
                        <Container className='col-10'>
                            <br/>
                            <h1> My Books </h1>
                            <br/>
                            <table className="table table-hover">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Book Name</th>
                                    <th scope="col">Book Description</th>
                                    <th scope="col">Days Left</th>
                                    <th scope="col">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {
                                    myBooks.map((row, index) => {
                                        return (
                                            <tr key={index}>
                                                <th scope="row">{index + 1}</th>
                                                <td>{row.data.book.bookTitle}</td>
                                                <td>{row.data.book.bookDescription}</td>
                                                <td>{calculateDaysLeft(index)}</td>
                                                <td><Button onClick={e => handleDownload(e, row.data.book.id)}>Read
                                                    Book</Button></td>
                                            </tr>
                                        )
                                    })
                                }
                                </tbody>
                            </table>
                        </Container> : <></>
                }
            </>
        </>
    )
}
export default withRouter(MyBooks)