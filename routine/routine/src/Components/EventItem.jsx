import React, {useState} from 'react';
import {Button} from 'react-bootstrap';
import EventForm from './EventForm';
import Modal from '../UI/Modal/Modal';
const EventItem = ({event,remove, change,dayId}) => {
  const [modal, setModal] = useState(false);
  const changeEvent = (event) =>{
    change(event);
    setModal(false);

  };
  return (

    /*<div className = "event">
      <div className ="event_content">
        <strong>{event.date}</strong>
        <div style = {{textAlign: 'right'}}>
          {event.description}
        </div>
      </div>
      <div style = {{textAlign: 'right'}}>
        <Button onClick = {() => setModal(true)}> change </Button>
        <Button  variant = "danger" onClick = {() => remove(event)}> remove </Button>
        <hr/>
      </div>
        <Modal show  = {modal} onHide = {setModal}> <EventForm createOrUpdate = {changeEvent} event = {event}/></Modal>
    </div>*/
    /*<div>

      <Card>
        <Card.Body>
          <Card.Title> {event.date} </Card.Title>
          <Card.Text style = {{textAlign: 'right'}}>
            {event.description}
          </Card.Text>
          <div style = {{textAlign: 'right'}}>
            <Button onClick = {() => change(event)}> change </Button>
            <Button  variant = "danger" onClick = {() => remove(event)}> remove </Button>
          </div>

        </Card.Body>
      </Card>
    </div>*/
    <div className = "day_item">
      <h1 style = {{textAlign: 'center'}}> {event.time} </h1>
      <h3 style = {{textAlign: 'right'}}> {event.description} </h3>
      <hr/>
      <div style = {{textAlign: 'right'}}>
        <Button onClick = {() => setModal(true)}> change </Button>
        <Button  variant = "danger" onClick = {() => remove(event)}> remove </Button>
        <Modal visible  = {modal} setVisible = {setModal}> <EventForm createOrUpdate = {changeEvent} event = {event} dayId = {dayId}/></Modal>
      </div>
    </div>
  );
}
export default EventItem;
