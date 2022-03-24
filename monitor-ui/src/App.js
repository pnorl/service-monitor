import React, {Component} from 'react';
//import AddService from "./AddService";
import ServiceList from "./ServiceList"
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import AddService from './AddService';

class App extends Component {
  render() {
    return (
      <Router>
      <Switch>
        <Route path='/' exact={true} component={ServiceList}/>
        <Route path='/new' component={AddService}/>
      </Switch>
    </Router>
    );
  }
}

export default App;
