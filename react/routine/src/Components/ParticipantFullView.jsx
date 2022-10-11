import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
const ParticipantFullView = ({id}) => {
  const [participant, setParticipant] = useState();
  useEffect ( () => {
    fetchParticipant();
  },
    1000
  );
  async function fetchParticipant(){
    const response = await ParticipantService.getFull(id);
    setParticipant(response);
  }
  return (

    <div>
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <div>
        {participant.events.map(event =>
          <div>
            <h1> {event.startTime} </h1>
            <h1> {event.endTime} </h1>
            <h1> {event.description} </h1>
          </div>
        )}
      </div>
    </div>
  )
}
export default ParticipantFullView;
