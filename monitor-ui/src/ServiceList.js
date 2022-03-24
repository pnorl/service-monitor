import React, { Component } from 'react';
import { Button, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

class ServiceList extends Component {

    constructor(props) {
        super(props);
        this.state = {services: []};
        this.statusIcon = this.statusIcon.bind(this)
        this.updateStatus = this.updateStatus.bind(this)
        this.statusStream = new EventSource(`http://localhost:8080/v1/services/statuses`);
    }

    updateStatus(updatedStatus){
        this.setState({services: this.state.services.map(service => service.id === updatedStatus.serviceId ? {...service, status:updatedStatus.status} : service)});
    }

    componentDidMount() {
        this.statusStream.onmessage = (e) => {
            this.updateStatus(JSON.parse(e.data));
        }
        fetch('/v1/services')
            .then(response => response.json())
            .then(data => this.setState({services: data}));   
    }

    componentWillUnmount() {
        console.log("unmounted")
        this.statusStream.close();
    }

    statusIcon(status) {
        if (status === "OK") {
            return (
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="green" className="bi bi-check-circle-fill" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                </svg>
            )

        } else if (status === "FAIL") {
            return (
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" className="bi bi-arrow-down-circle-fill" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v5.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V4.5z"/>
                </svg>
            )
        } else {
            return null
        }
    }

    render() {
        const {services, isLoading} = this.state;
    
        if (isLoading) {
            return <p>Loading Services</p>;
        }
        
        const serviceList = services.map(service => {
            return <tr key={service.id}>
                <td>{service.name}</td>
                <td>{service.url}</td>
                <td>{new Date(service.created).toLocaleString()}</td>
                <td>{this.statusIcon(service.status)} {service.status}</td>
            </tr>
        });
    
        return (
            <div>
                <Container fluid className="mt-4">
                    <div className="float-end">
                        <Button color="success" tag={Link} to="/new">Add Service</Button>
                    </div>
                    <h3>Services</h3>
                    <Table className="mt-4">
                        <thead>
                            <tr>
                                <th width="20%">Name</th>
                                <th width="30%">URL</th>
                                <th width="20%">Created</th>
                                <th width="10%">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                        {serviceList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }

}
export default ServiceList;