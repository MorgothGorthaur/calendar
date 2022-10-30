import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import ParticipantItem from './ParticipantItem';
import ParticipantForm from './ParticipantForm';
import {Button, Modal} from 'react-bootstrap';
import Loader from '../UI/Loader/Loader';
const ParticipanList = () => {
  const [participants, setParticipants] = useState([]);
  const [modal, setModal] = useState(false);
  const [loading, setLoading] = useState(false);
  useEffect ( () => {
    setLoading(true);
    setTimeout ( () => {
      fetchParticipant();
      setLoading(false);
    }, 1000);
  },[]);
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

  const changeParticipant = (participant) => {
    setParticipants([...participants.filter(p => p.id !== participant.id), participant]);
  }
  return(
    <div>
      {loading ? (
          <div style = {{display: 'flex', justifyContent: 'center'}}>
            <Loader />
          </div>
          ):(
      <div className = "list">
        {participants.map(participant =>
          <div className = "participant_list">
            <ParticipantItem participant = {participant} key = {participant.id} remove = {removeParticipant} change = {changeParticipant}/>
          </div>
        )}
        <div style = {{textAlign: 'right'}}>
          <Button variant = "primary" onClick = {() => setModal(true)}> add </Button>
          <Modal show = {modal} onHide = {setModal}> <ParticipantForm createOrUpdate = {addParticipant}/> </Modal>
        </div>
      </div>
      )}
    </div>
  )
}
export default ParticipanList;
