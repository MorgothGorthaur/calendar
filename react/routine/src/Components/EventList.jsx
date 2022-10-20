import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import EventItem from './EventItem';
import EventForm from './EventForm';
import EventService from '../API/EventService';
import {Button, Modal} from 'react-bootstrap';
import Loader from '../UI/Loader/Loader';
const EventList = ({participantId}) => {
  const[events, setEvents] = useState([]);
  const[modal, setModal] = useState(false);
  const [loading, setLoading] = useState(false);
  async function fetchEvent(){
    const response = await ParticipantService.getFull(participantId);
    setEvents(response);
  };

  useEffect ( () => {
    setLoading(true);
    setTimeout ( () => {
      fetchEvent();
      setLoading(false);
    }, 1000);
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
    fetchEvent();
  }
  return (
    <div>
      {loading ? (
          <div style = {{display: 'flex', justifyContent: 'center'}}>
            <Loader />
          </div>
      ):(
        <div>
          {events.length ? (
            <div className = "participant_list">
              {events.map(event =>
                  <div>
                    <EventItem id = {participantId} event = {event} remove = {removeEvent} change = {changeEvent} />
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
        )}
    </div>
  )
}
export default EventList;
