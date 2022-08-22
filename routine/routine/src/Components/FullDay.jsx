import React, {useState} from 'react';
import {Button} from 'react-bootstrap';
import Modal from '../UI/Modal/Modal';
import DayForm from './DayForm';
import EventForm from './EventForm';
import EventsList from './EventsList';
import EventService from '../API/EventService';
const FullDay = ({day, events, update,setEvents}) =>{

  const [modal, setModal] = useState(false);
  const [eventView, setEventView] = useState(false);
  const updateDay = (newDay) =>{
    setModal(false)
    update(newDay);
  };

  const removeEvent = (event) => {
    EventService.removeEvent(event.id);
    setEvents(events.filter(d => d.id !== event.id ))
  };
  const changeEvent = (event) => {
    setEvents([...events.filter(d => d.id !== event.id ), event])
  };
  const addNewEvent = (event) => {
    setEvents([...events,event])
    setEventView(false)
  };
  return (
    <div className = "day_item">
      <h1  style = {{textAlign: 'center'}}>{day.name} </h1>
      <h3  style = {{textAlign: 'right'}}> {day.date} </h3>
      <hr/>
      <div style = {{textAlign: 'right'}} >
        <Button onClick = { () => setModal(true)}> change day </Button>
        <EventsList events = {events}  remove = {removeEvent} change = {changeEvent} add = {addNewEvent} dayId = {day.id}/>
        <Button onClick = {() => setEventView(true) } > add Event </Button>
        <Modal visible = {modal} setVisible = {setModal}> <DayForm createOrUpdate = {updateDay} day = {day}/> </Modal>
        <Modal visible = {eventView} setVisible = {setEventView}> <EventForm createOrUpdate = {addNewEvent} dayId = {day.id}/> </Modal>
      </div>
    </div>
  )
};
export default FullDay;
