import axios from "axios";

let host = `13.233.77.149`
let port = 8989

class APIService {

    static config = {
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }

    static getServerLogs() {
        return axios.get(`http://${host}:${port}/access/logs`)
    }

    static getBookData(bookId) {
        return axios.get(`http://${host}:${port}/books/${bookId}`)
    }

    static getAllBookRequests() {
        return axios.get(`http://${host}:${port}/access/requests`)
    }

    static getIssuedBooks(emailId) {
        return axios.get(`http://${host}:${port}/access/${emailId}`, APIService.config)
    }

    static getAllUsers() {
        return axios.get(`http://${host}:${port}/user/all`, APIService.config)
    }

    static getAccessibleData() {
        return axios.get(`http://${host}:${port}/books`, APIService.config)
    }

    static getSearchedData(body) {
        return axios.post(`http://${host}:${port}/books/search`, body, APIService.config)
    }

    static getBook(id) {
        return axios.get(`http://${host}:${port}/books/download/${id}`, APIService.config)
    }

    static getAccessesByEmail(emailId) {
        return axios.get(`http://${host}:${port}/access/${emailId}`, APIService.config)
    }

    static UpdateAccessByCSV(formData) {
        return axios.post(`http://${host}:${port}/access/update`, formData, APIService.config)
    }

    static addBook(formData) {
        return axios.post(`http://${host}:${port}/books/add`, formData, APIService.config)
    }

    static deleteBook(bookToDelete) {
        return axios.get(`http://${host}:${port}/books/delete/${bookToDelete}`, APIService.config)
    }

    static authUser(email, password) {
        const body = {
            email: email,
            password: password
        }
        
        return axios.post(`https://${host}:${port}/user/auth`, body, APIService.config)
    }

    static registerUser(email, userName, password) {
        const user = {
            email: email,
            userName: userName,
            password: password
        }

        let body = JSON.stringify(user)
        console.log(user)
        
        return axios.post(`https://${host}:${port}/user/registration`, body, APIService.config)
    }

    static getAccessRequestsByEmail(userEmail) {
        return axios.get(`http://${host}:${port}/access/requests/${userEmail}`, APIService.config)
    }

    static downloadBookByID(bookId) {
        return axios.get(`http://${host}:${port}/books/download/${bookId}`, APIService.config)
    }

    static requestAccess(bookId, userEmail) {
        const body = {
            bookId: bookId,
            emailId: userEmail
        }
        
        return axios.post(`http://${host}:${port}/access/request`, body, APIService.config)
    }

    static approveAccess(BookId, EmailId, RequestId) {
        const body = {
            bookId: BookId,
            requestId: RequestId,
            emailId: EmailId
        }
        
        return axios.post(`http://${host}:${port}/access/approve`, body, APIService.config)
    }

    static rejectAccessRequest(BookId, EmailId, RequestId) {
        const body = {
            bookId: BookId,
            requestId: RequestId,
            emailId: EmailId
        }
        return axios.post(`http://${host}:${port}/access/reject`, body, APIService.config)
    }
}

export {
    APIService
}