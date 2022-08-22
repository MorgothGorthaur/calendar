
import './App.css';
import React from 'react';
import logo from './logo.svg';
import {Navbar, Container} from 'react-bootstrap';
import DaysList from './Components/DaysList';

function App() {
  return (
    <>
    <div>
    <Navbar bg="dark">
      <Container>
        <Navbar.Brand href="#home">
          <img
            src={logo}
            width="50"
            height="50"
            className="d-inline-block align-top"
            alt="React Bootstrap logo"
          />
        </Navbar.Brand>
      </Container>
    </Navbar>
    <br />
    </div>
    <div>
      <DaysList />
    </div>
    </>
  );
}

export default App;
