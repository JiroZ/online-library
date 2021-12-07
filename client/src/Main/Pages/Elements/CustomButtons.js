import {Button, Container} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import axios from "axios";
import {APIService} from "../../../Services/APIService";
import user from "../../../Redux/UserLogin/Reducers/User";


const BookActionButton = (props) => {
    const userEmail = useSelector(state => state.getUser.user.email)
    const issuedBooks = props.IssuedBooks
    const bookId = props.bookId

    const [requestExists, setRequestExists] = useState(false)

    const [haveAccess, setHaveAccess] = useState(false)

    const [buttonDisabled, setButtonDisabled] = useState(true)

    useEffect(() => {
        for(let issuedBook of issuedBooks) {
            if(issuedBook.bookId === bookId) {
                setHaveAccess(true)
            }
        }

        APIService.getAccessRequestsByEmail(userEmail).then(response => {
            for (let data of response.data) {
                if (data.bookId === bookId) {
                    setRequestExists(true)
                }
            }
            setButtonDisabled(false)

        }).catch(err => {
            console.log('Error while fetching the book requests of the user', err)
            console.log('Error while fetching the book requests of the user server response: ', err.response)
        })
    }, [userEmail, issuedBooks, bookId])

    function handleDownload(e) {
        e.preventDefault()

        APIService.downloadBookByID(bookId).then(response => {
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

    function handleRequest(e) {
        e.preventDefault()
        setButtonDisabled(true)

        APIService.requestAccess(bookId, userEmail).then(response => {
            console.log(response)
            window.location.reload()
        }).catch(err => {
            console.warn('Error while making request for book', err)
            console.warn('Error while making request for book server response: ', err.response)
        })
    }

    function generateActionButton() {
        return haveAccess ?
            <Button disabled={buttonDisabled} onClick={e => handleDownload(e)}>Read Book</Button> :
            <Button disabled={buttonDisabled} onClick={e => handleRequest(e)}>Request Access</Button>;
    }

    return (
        <>
            {
                <div>
                    {
                        !requestExists ?
                            <div>
                                {
                                    generateActionButton()
                                }
                            </div> :
                            <label>Requested</label>
                    }
                </div>
            }
        </>
    )
}

const BookRequestActionButtons = (props) => {

    const [disableButton, setDisableButton] = useState(false)

    function handleApproveRequest(e) {
        e.preventDefault()
        setDisableButton(true)

        APIService.approveAccess(props.BookId, props.EmailId, props.RequestId).then(response => {
            console.log(response.data)
            window.location.reload();
        }).catch(err => {
            console.warn("Error while approving request book", err)
            console.warn("Error while approving request book server response: ", err.response)
        })
    }

    function handleDeclineRequest(e) {
        e.preventDefault()
        setDisableButton(true)

        APIService.rejectAccessRequest(props.BookId, props.EmailId, props.RequestId).then(response => {
            console.log(response.data)
            window.location.reload();
        }).catch(err => {
            console.warn("Error while approving request book", err)
            console.warn("Error while approving request book server response: ", err.response)
        })
    }

    return (
        <>
            <Container>
                <Button disabled={disableButton} onClick={e => handleApproveRequest(e)}>Approve</Button>
                <Button disabled={disableButton} onClick={e => handleDeclineRequest(e)}>Decline</Button>
            </Container>
        </>
    )
}
export {
    BookActionButton,
    BookRequestActionButtons
}