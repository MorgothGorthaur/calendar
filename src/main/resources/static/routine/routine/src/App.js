
import './App.css';
import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import ReactDOM from 'react-dom';
import {Button, Navbar, Container} from 'react-bootstrap';
import DaysList from './Components/DaysList';
import DayForm from './Components/DayForm';
import Modal from './UI/Modal/Modal';
import DaysService from './API/DaysService';
function App() {
const [days, setDays] =  useState([]);
const removeDay = (day) =>{
  DaysService.removeDay(day.id);
  setDays(days.filter(d => d.id !== day.id ))
};
const createDay = (newDay) =>  {
      setDays([...days, newDay])
      setModal(false)
};
const updateDay = (newDay) => {
  setDays([...days.filter(d => d.id !== newDay.id ), newDay])
  setModal(false)
}



useEffect ( () => {
  fetchDays();
},[]);
async function fetchDays() {
  const response = await DaysService.getAll();
  setDays(response);
}



const [modal, setModal] = useState(false);
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
    <div className = "day_item">
      <DaysList days = {days} remove = {removeDay} update = {updateDay}/>
      <Modal visible = {modal} setVisible = {setModal}> <DayForm createOrUpdate = {createDay}/> </Modal>
      <Button onClick = { () => setModal(true)}> add new day </Button>
    </div>
    </>
  );
}

export default App;
