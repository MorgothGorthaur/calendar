import React, {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import EventForm from './EventForm';
const EventItem = ({id, event, remove, change}) => {
  const[show, setShow] = useState(false);
  const[modal, setModal] = useState(false);
  const update = (data) => {
    setModal(false);
    change(data);
  };
  return (
      <div>
        <h1>{event.description}</h1>
        {show ? (
          <div>
            <h1> {event.startTime}  </h1>
            <h1> {event.endTime} </h1>
            <Button variant = "dark" onClick = {() => setShow(false)}> close </Button>
            <Button variant = "primary" onClick = {() => setModal(true)}> change </Button>
            <Modal show = {modal} onHide = {setModal} > <EventForm id = {id} event = {event} createOrUpdate = {update}/> </Modal>
          </div>
        ):(
          <div>
            <Button variant = "danger" onClick = {() => remove(event.id)}> remove </Button>
            <Button variant = "primary" onClick = {() => setShow(true)}> open </Button>
          </div>
        )}
      </div>
  )
};
export default EventItem;
