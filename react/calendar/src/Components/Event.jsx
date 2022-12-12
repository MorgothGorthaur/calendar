import {Button, Modal} from 'react-bootstrap';
import React, {useState} from 'react';
import EventForm from './EventForm';
const Event = ({tokens, event, change, remove}) => {
  const [modal, setModal] = useState(false);
  const update = (data) => {
    setModal(false);
    change(data);
  }
  return (
    <div className = "participant_item">
      <h1> {event.description} </h1>
      <h1> {event.startTime} </h1>
      <h1> {event.endTime} </h1>
      <Button onClick = {() => setModal(true)}> change </Button>
      <Button variant = "danger" onClick = {() => remove(event)}> remove </Button>
      <Modal show = {modal} onHide = {setModal}> <EventForm tokens = {tokens} event = {event}  CreateOrUpdate = {update}/> </Modal>
    </div>
  );
};
export default Event;
