import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
const Participant = ({tokens, setModal}) => {
  const [participant, setParticipant] = useState('');
  useEffect ( () => {
    fetchParticipant ();
  },[]);
  async function fetchParticipant () {
    const response = await ParticipantService.getParticipant(tokens.access_token);
    if(response.hasError){
      setModal(false);
    }
    setParticipant (response);
  };
  return (
    <div className = "participantItem">
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <h1> {participant.email} </h1>
    </div>
  );
};
export default Participant;
