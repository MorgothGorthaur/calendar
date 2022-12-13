import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import {Button, Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
import LoginService from '../API/LoginService';
import EventList from './EventList';
const Participant = ({tokens, setTokens, setModal}) => {
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
    ParticipantService.delete(tokens).then(data => {
      console.log(data);
      if(data.hasError) {
        LoginService.refresh(tokens).then(data => {
          if(data.hasError){
            alert("you must relogin")
          } else {
            setTokens(data, tokens.refresh_token);
            ParticipantService.delete(data);
          };
        });
      };
    });
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
      <div style = {{textAlign: 'left'}}>
        <h1> {participant.firstName} </h1>
        <h1> {participant.lastName} </h1>
      </div>
      <h3> {participant.email} </h3>
      <div style = {{textAlign: 'center'}} >
        <Modal show = {show} onHide = {setShow} > <ParticipantForm CreateOrUpdate = {change} participant = {participant} tokens = {tokens} setTokens = {setTokens} /></Modal>
        {
          events ?
          (
            <>
              <EventList tokens = {tokens} setTokens = {setTokens} />
              <Button variant = "dark" onClick = {() => setEvents(false)}> close </Button>
            </>
          ) : (
            <>
              <Button onClick = {() => setShow(true)} > change </Button>
              <Button variant = "danger" onClick = {() => remove()} > delete </Button>
              <Button onClick = {() => setEvents(true)}> events </Button>
              <br/>
            </>
          )
        }
      </div>
    </div>
  );
};
export default Participant;
