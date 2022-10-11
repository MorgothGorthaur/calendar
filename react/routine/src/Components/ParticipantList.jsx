import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import ParticipantItem from './ParticipantItem';
import ParticipantForm from './ParticipantForm';
import {Button, Modal} from 'react-bootstrap';
const ParticipanList = () => {
  const [participants, setParticipants] = useState([]);
  const [modal, setModal] = useState(false);
  useEffect ( () => {
    fetchParticipant();
  },1000);
  async function fetchParticipant(){
    const response = await ParticipantService.getAll();
    setParticipants(response);
  };

  const removeParticipant = (id) => {
    ParticipantService.delete(id);
    setParticipants([...participants.filter(p => p.id !== id)]);
  };

  const addParticipant = (participant) => {
    setParticipants([...participants, participant]);
    setModal(false);
  };
  return(
    <div>
      {participants.map(participant =>
        <div>
          <ParticipantItem participant = {participant} key = {participant.id}/>
          <div>
            <Button variant = "danger" onClick = {() => removeParticipant(participant.id)}> remove </Button>
          </div>
        </div>
      )}
      <div>
        <Button variant = "primary" onClick = {() => setModal(true)}> add </Button>
        <Modal show = {modal} onHide = {setModal}> <ParticipantForm createOrUpdate = {addParticipant}/> </Modal>
      </div>
    </div>
  )
}
export default ParticipanList;
