import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import ParticipantItem from './ParticipantItem';
const ParticipanList = () => {
  const [participants, setParticipants] = useState([]);

  useEffect ( () => {
    fetchParticipant();
  },1000);
  async function fetchParticipant(){
    const response = await ParticipantService.getAll();
    setParticipants(response);
    console.log(participants)
  }
  return(
    <div>
      {participants.map(participant =>
        <ParticipantItem participant = {participant} key = {participant.id}/>
      )}
    </div>
  )
}
export default ParticipanList;
