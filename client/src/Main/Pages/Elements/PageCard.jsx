import {Card} from "react-bootstrap";
import React from "react";
import { useHistory } from 'react-router-dom';

const PageCard = (props) => {
    const history = useHistory()

    const redirectBlog = (e) => {
        history.push("/blog/" + props.blogData.id)
        window.location.reload()
    }

    return (
        <Card key={props.blogData.id} className="bg-dark text-white" onClick={(e) => redirectBlog(e)}>
            <Card.Img
                src="https://image.shutterstock.com/image-vector/set-100-geometric-shapes-memphis-260nw-1511671634.jpg"
                alt="Card image"/>

            <Card.Title>{props.blogData.blogTitle}</Card.Title>
            <Card.Text style={{width: 100}}>
                {props.blogData.data}
            </Card.Text>

            <Card.Footer>
                <Card.Text>{"Added on " + props.blogData.date.substring(0, 10).replaceAll('-', "/")}</Card.Text>
            </Card.Footer>
        </Card>
    )
}
export {
    PageCard
}