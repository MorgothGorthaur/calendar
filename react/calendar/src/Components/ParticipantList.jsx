import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
const ParticipantList = () => {
  const [participants, setParticipants] = useState([]);
  useEffect ( () => {
    fetchParticipants ();
  },[]);
  async function fetchParticipants () {
    const response = await ParticipantService.getAll();
    setParticipants (response);
  };
  return (
    <div>
      { participants.length ? (
        <div className = "participant_list">
          { participants.map ( participant =>
            <div className = "participant_item">
              <h1> {participant.firstName} </h1>
              <h1> {participant.lastName} </h1>
            </div>
          )}
        </div>
      ) : (
        <h1> length not found! </h1>
      )}
    </div>
  );
};
export default ParticipantList;
