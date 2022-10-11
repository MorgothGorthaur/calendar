import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import {Button} from 'react-bootstrap';
const EventList = ({id}) => {
  const[events, setEvents] = useState([]);
  async function fetchEvent(){
    const response = await ParticipantService.getFull(id);
    setEvents(response.events);
  };
  useEffect ( () => {
    fetchEvent();
  },1000);
  return (
    <div>
      {events.length ? (
        <div>
          {events.map(event =>
              <div>
                <h1> {event.description} </h1>
                <Button variant = "danger"> delete </Button>
              </div>
          )}

        </div>
      ):(
        <h1> not found! </h1>
      )}
    </div>
  )
}
export default EventList;
