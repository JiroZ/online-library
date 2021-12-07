import React from "react";
import {Redirect, BrowserRouter} from "react-router-dom";
import {Col, Row} from "react-bootstrap";
import './Home.css'
import {PageCard} from "./Elements/PageCard";

const HandleSearchRedirect = (props) => {
    const searchData = props.seachData

    console.log(searchData)
    return (
        <>
            <Redirect to='/search'/>
            <BrowserRouter>
                <div className=' centered contentContainer'>
                    {
                        (searchData.length > 0) ?
                            <Row xs={1} md={2} className="g-4">
                                {Array.from({length: 1}).map((_, idx) => (
                                    searchData.map(blogData => {
                                        return (
                                            <Col>
                                                {
                                                    <PageCard blogData={blogData}/>
                                                }
                                            </Col>
                                        )
                                    })
                                ))}
                            </Row> :
                            <div>
                                No results found
                            </div>
                    }
                </div>
            </BrowserRouter>
        </>
    )
}

export {
    HandleSearchRedirect
}