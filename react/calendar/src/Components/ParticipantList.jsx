import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import {Button, Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
const ParticipantList = () => {
  const [participants, setParticipants] = useState([]);
  const [modal, setModal] = useState(false);
  const add = (participant) => {
    setParticipants([...participants, participant]);
    setModal(false);
  }
  useEffect ( () => {
    fetchParticipants ();
  },[]);
  async function fetchParticipants () {
    const response = await ParticipantService.getAll();
    setParticipants (response);
  };
  return (
    <div className = "participant_list">
      { participants.length ? (
        <div>
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
      <Button onClick = {() => setModal(true)}> add </Button>
      <Modal show = {modal} onHide = {setModal}> <ParticipantForm CreateOrUpdate = {add}/> </Modal>
    </div>
  );
};
export default ParticipantList;
