import {Button, Col, Container, Form} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {APIService} from "../../../../../Services/APIService";
import {BookRequestActionButtons} from "../../../../Pages/Elements/CustomButtons";

const ManageAccessesTab = () => {

    const [field, setField] = useState([]);

    const [csv, setCSV] = useState()

    const handleSelectionSubmit = (e) => {
        e.preventDefault();

        let accessPromises = [];
        for (let emailId of field) {
            accessPromises.push(APIService.getAccessesByEmail(emailId))
        }
        Promise.all(accessPromises).then(accesses => {
            const csvData = []
            for (let i = 0; i < field.length; i++) {
                console.log(accesses[i].data)
                csvData.push([field[i], JSON.stringify(accesses[i].data).replaceAll(',', '|||')])
            }

            let csvContent = "data:text/csv;charset=utf-8,"
                + csvData.map(data => data.join(",")).join("\n");

            let encodedUri = encodeURI(csvContent);
            let link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", `selected_accesses.csv`);
            document.body.appendChild(link); // Required for FF

            link.click();
        }).catch(err => {
            console.warn('An Error occurred while fetching accesses', err)
            console.warn('An Error occurred while fetching accesses server response : ', err.response)
        }).finally(

        )
    }

    const handleFileChange = (e) => {
        e.preventDefault()
        setCSV(e.target.files[0])
    }

    const handleCSVSubmit = (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('csvFile', csv);

        APIService.UpdateAccessByCSV(formData).then(response => {
            console.log(response.data)
        }).catch((err) => {
            console.warn('error during http call CSV Upload', err);
            console.warn('error during http call CSV Upload Response: ', err.response);
        });
    }

    const [users, setUsers] = useState([])

    useEffect(() => {
        APIService.getAllUsers()
            .then(response => {
                setUsers(response.data)
            }).catch(function (ex) {
            console.log('Response parsing failed. Error: ', ex);
            console.log('Response parsing failed. Error: Server Response', ex.response);
        });
    }, [])


    return (
        <>
            <Container className="mb-3 col-7 justify-content-end">

                <Form.Group as={Col} controlId="my_multiselect_field">
                    <Form.Label>Select users to download access csv file</Form.Label>
                    <Form.Control as="select" multiple
                                  onChange={e => setField([].slice.call(e.target.selectedOptions).map(item => item.value))}>
                        {
                            users.map((user, index) => {
                                return (
                                    <option value={user.email} key={index}>
                                        {user.email}
                                    </option>
                                )
                            })
                        }
                    </Form.Control>
                    <br/>
                    <Button variant="primary" type="submit" onClick={(e) => handleSelectionSubmit(e)}>
                        Download CSV
                    </Button>
                </Form.Group>
                <hr/>
                <br/>
                <br/>


                <Form.Group controlId="formFileLg" className="mb-3">
                    <Form.Label>Upload the CSV file to update accesses in format of Name[1] and
                        BookAccesses[1,2,3.....] or download the access file to confirm the format</Form.Label>
                    <Form.Control type="file" name='csv' onChange={e => handleFileChange(e)} size="lg"/>
                    <br/>
                    <Button variant="primary" type="submit" onClick={(e) => handleCSVSubmit(e)}>
                        Upload CSV
                    </Button>
                </Form.Group>
            </Container>
        </>
    )
}

const ManageBooksTab = () => {

    const [book, setBook] = useState()
    const [bookTitle, setBookTitle] = useState('')
    const [bookDescription, setBookDescription] = useState('')
    const [bookToDelete, setBookToDelete] = useState()


    const handleBookAddSubmit = (e) => {
        e.preventDefault();

        const formData = new FormData()

        formData.append('bookTitle', bookTitle)
        formData.append('bookDescription', bookDescription)
        formData.append('bookFile', book)

        APIService.addBook(formData).then(response => {
            console.log(response.data)
            window.location.reload()
        }).catch((err) => {
            console.warn('error during http call adding book', err);
            console.warn('error during http call adding book Response: ', err.response);
        });
    }

    const handleBookDeleteSubmit = (e) => {
        e.preventDefault();

        APIService.deleteBook(bookToDelete).then(response => {
            console.log(response.data)
            window.location.reload()
        }).catch((err) => {
            console.warn('error during http call deleting book', err);
            console.warn('error during http call deleting book Response: ', err.response);
        });
    }

    const handleFileChange = (e) => {
        setBook(e.target.files[0])
    }

    const handleDeleteSelectionSubmit = (e) => {
        setBookToDelete(e)
    }

    const [books, setBooks] = useState([])

    useEffect(() => {
        APIService.getAccessibleData()
            .then(response => {
                setBooks(response.data)
            }).catch(function (ex) {
            console.log('User Response parsing failed. Error: ', ex);
            console.log('User Response parsing failed. Error: Server Response', ex.response);
        });
    }, [])

    function handleDeleteSelection(e) {
        setBookToDelete(e.target.value)
    }

    return (
        <>
            <Container className="col-5">
                <Form.Group controlId="formFileLg" className="mb-3">
                    <Form.Label> <strong>Add Book</strong> </Form.Label>
                    <br/>
                    <Form.Label>Book Title</Form.Label>
                    <Form.Control type="textfield" onChange={e => setBookTitle(e.target.value)}
                                  placeholder='Enter Title'/>
                    <Form.Label>Book Description</Form.Label>
                    <Form.Control type="textarea" onChange={e => setBookDescription(e.target.value)}
                                  placeholder='Enter Description'/>

                    <Form.Label>Upload Book PDF</Form.Label>
                    <Form.Control type="file" accept='.pdf' size="lg" onChange={e => {
                        handleFileChange(e)
                    }}/>
                    <br/>
                    <Button variant="primary" type="submit" onClick={(e) => handleBookAddSubmit(e)}>
                        Add Book
                    </Button>
                </Form.Group>

                <hr/>
                <br/>
                <Form.Group controlId="formFileLg" className="mb-3">
                    <Form.Label> <strong>Delete Book</strong> </Form.Label>
                    <br/>
                    <Form.Label>Select Book to delete</Form.Label>
                    <Form.Control as="select" onChange={e => handleDeleteSelection(e)}>
                        {
                            books.map((bookIndex, index) => {
                                return (
                                    <option value={bookIndex.id} key={index}>{bookIndex.bookTitle} : {bookIndex.id}</option>
                                )
                            })
                        }
                    </Form.Control>
                    <br/>
                    <Button variant="primary" type="submit" onChange={e => handleDeleteSelectionSubmit(e)}
                            onClick={(e) => handleBookDeleteSubmit(e)}>
                        Delete Book
                    </Button>
                </Form.Group>
            </Container>
        </>
    )
}

const ManageBookRequestsTab = () => {

    const [bookRequests, setBookRequests] = useState([])

    useEffect(() => {
        APIService.getAllBookRequests().then(response => {
            setBookRequests(response.data)
        }).catch(err => {
            console.log('Error while getting book requests list: ', err)
            console.log('Error while getting book requests list server response: ', err.response)
        })
    }, [])

    if(bookRequests.length < 1) {
        return (
            <>
            <h3>No Requests Found</h3>
            </>
        )
    } else {
        return (
            <>
                <Container className='col-10'>
                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Request ID</th>
                            <th scope="col">Book ID</th>
                            <th scope="col">Request made by</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            bookRequests.map((row, index) => {
                                return (
                                    <tr key={row.requestId}>
                                        <th scope="row">{index + 1}</th>
                                        <td>{row.requestId}</td>
                                        <td>{row.bookId}</td>
                                        <td>{row.emailId}</td>
                                        <td>
                                            <BookRequestActionButtons RequestId={row.requestId} BookId={row.bookId} EmailId={row.emailId}/>
                                        </td>
                                    </tr>
                                )
                            })
                        }
                        </tbody>
                    </table>
                </Container>
            </>
        )
    }
}

const ServerLogsTab = () => {
    const [logs, setLogs] = useState([])

    useEffect(() => {
        APIService.getServerLogs().then(response => {
            setLogs(response.data)
        }).catch(err => {
            console.warn("An exception occurred while loading server logs", err)
            console.warn("An exception occurred while loading server logs server response: ", err.response)
        })
    }, [])

    return (
        <>
            <Container className='col-10'>
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Log ID</th>
                        <th scope="col">Action</th>
                        <th scope="col">Log</th>
                        <th scope="col">Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        logs.map((row, index) => {
                            return (
                                <tr key={row.logId}>
                                    <th scope="row">{index + 1}</th>
                                    <td>{row.logId}</td>
                                    <td>{row.action}</td>
                                    <td>{row.message}</td>
                                    <td>{row.date}</td>
                                </tr>
                            )
                        })
                    }
                    </tbody>
                </table>
            </Container>
        </>
    )
}
export {
    ManageAccessesTab,
    ManageBooksTab,
    ManageBookRequestsTab,
    ServerLogsTab
}