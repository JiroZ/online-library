import React from 'react'
import {withRouter} from "react-router-dom";
import {Tab, Tabs} from "react-bootstrap";
import {ManageAccessesTab, ManageBookRequestsTab, ManageBooksTab, ServerLogsTab} from "./admin/AdminActionTabs";


const AdminPanel = () => {
    return (
        <>
            <br/>
            <h1>Admin Panel</h1>
            <br/>
            <Tabs
                defaultActiveKey="home"
                transition={false}
                id="noanim-tab-example"
                className="mb-3"
            >
                <Tab eventKey="accesses" title="Manage User Accesses">
                    <ManageAccessesTab/>
                </Tab>
                <Tab eventKey="books" title="Manage Books">
                    <ManageBooksTab/>
                </Tab>
                <Tab eventKey="requests" title='Book Requests'>
                    <ManageBookRequestsTab/>
                </Tab>
                <Tab eventKey="server-logs" title='Server Logs'>
                    <ServerLogsTab/>
                </Tab>
            </Tabs>
        </>
    )
}
export default withRouter(AdminPanel)