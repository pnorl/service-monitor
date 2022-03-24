import React, { Component } from 'react'
import { withRouter, Link } from 'react-router-dom'
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

class AddService extends Component {
    service = {
        name: '',
        url: '',
    }

    constructor(props) {
        super(props);
        this.state = {
            item: this.service
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
    
        await fetch('/v1/services', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/');
    }

    render() {
        const {item} = this.state;
        return (
            <Container className="mt-4">
                <h2>Add Service</h2>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                            onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="url">URL</Label>
                        <Input type="text" name="url" id="url" value={item.url || ''}
                            onChange={this.handleChange} autoComplete="url"/>
                    </FormGroup>
                    <FormGroup className="mt-2">
                        <Button color="primary" type="submit">Create</Button>{' '}
                        <Button color="secondary" tag={Link} to="/">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        )
    }

}

export default withRouter(AddService);