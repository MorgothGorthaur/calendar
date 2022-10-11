import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
const EventList = ({id}) => {
  const[events, setEvents] = useState([]);
  async function fetchEvent(){
    const response = await ParticipantService.getFull(id);
    setEvents(response);

  };
  useEffect ( () => {
    fetchEvent();
  },1000);
  return (
    <div>
      {events.lenhth ? (
        <h1> ggg </h1>
      ):(
        <h1> not found! </h1>
      )}
    </div>
  )
}
export default EventList;
