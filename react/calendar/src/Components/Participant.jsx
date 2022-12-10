import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import {Button, Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
const Participant = ({tokens, setModal}) => {
  const [participant, setParticipant] = useState('');
  const [show, setShow] = useState(false);
  const [events, setEvents] = useState(false)
  useEffect ( () => {
    fetchParticipant ();
  },[]);

  const change = (data) => {
    if(data){
      window.location.reload(false);
    }
  };
  const remove = () => {
    ParticipantService.delete(tokens);
  };
  async function fetchParticipant () {
    const response = await ParticipantService.getParticipant(tokens.access_token);
    if(response.hasError){
      setModal(false);
    }
    setParticipant (response);
  };
  return (
    <div className = "participant_item">
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <h1> {participant.email} </h1>
      <Button onClick = {() => setShow(true)} > change </Button>
      <Button variant = "danger" onClick = {() => remove()} > delete </Button>
      <Modal show = {show} onHide = {setShow} > <ParticipantForm CreateOrUpdate = {change} participant = {participant} tokens = {tokens} /></Modal>
      <Button onClick = {() => setEvents(true)}> events </Button>
      {
        events ?
        (
          <h1> there must be list of events! </h1>
        ) : (
          <br/>
        )
      }
    </div>
  );
};
export default Participant;
