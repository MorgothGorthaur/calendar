import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import EventItem from './EventItem';
import EventForm from './EventForm';
import EventService from '../API/EventService';
import {Button, Modal} from 'react-bootstrap';
const EventList = ({participantId}) => {
  const[events, setEvents] = useState([]);
  const[modal, setModal] = useState(false);
  async function fetchEvent(){
    const response = await ParticipantService.getFull(participantId);
    setEvents(response);
  };

  useEffect ( () => {
    fetchEvent();
  },1000);

  const removeEvent = (id) => {
    EventService.remove(participantId, id);
    setEvents([...events.filter(e => e.id !== id)]);
  };


  const addEvent = (event) => {
    setEvents([...events, event]);
    setModal(false);
  };

  const changeEvent = (event) => {
    setEvents([...events.filter(e => e.id !== event.id), event]);
  }
  return (
    <div>
      {events.length ? (
        <div className = "participant_list">
          {events.map(event =>
              <div>
                <EventItem event = {event} remove = {removeEvent} change = {changeEvent} />
              </div>
          )}

        </div>
      ):(
        <h1> not found! </h1>
      )}
      <div style = {{textAlign: 'center'}}>
        <Button variant = "primary" onClick = {() => setModal(true)}> add </Button>
        <Modal show = {modal} onHide = {setModal}> <EventForm createOrUpdate = {addEvent} id = {participantId}/> </Modal>
      </div>
    </div>
  )
}
export default EventList;
