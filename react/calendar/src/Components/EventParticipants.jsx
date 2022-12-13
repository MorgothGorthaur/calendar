import React, {useState, useEffect} from 'react';
import EventService from '../API/EventService';
import {Button} from 'react-bootstrap';
import EmailForm from './EmailForm';
import LoginService from '../API/LoginService';
const EventParticipants = ({tokens, setTokens, id}) => {
  const [participants, setParticipants] = useState([]);
  const [show, setShow] = useState(false);
  useEffect ( () => {
    fetchParticipants();
  }, []);

  async function fetchParticipants () {
      const response = await EventService.getParticipants(tokens, id);
      if(response.error_message) {
        LoginService.refresh(tokens).then(refresh => {
          if(refresh.hasError){
            alert("you must relogin")
          } else {
            setTokens(refresh, tokens.refresh_token);
            console.log(refresh);
            EventService.getParticipants(refresh, id).then(d => {setParticipants(d);})

          };
        });
      } else {
        setParticipants(response);
      }
  }
  const update = (data) => {
    fetchParticipants();
    setShow(false);
  }
  return (
    <div>
      {
        participants.length !== 1 ? (
          <div>
            {
              participants.map ( participant =>
                <h1> {participant.firstName} </h1>
              )
            }
          </div>
        ) : (
          <h1> this is only your event! </h1>
        )
      }
      <div>
      {
        show ? (
          <EmailForm tokens = {tokens} setTokens = {setTokens} id = {id} update = {update}/>
        ) : (
          <div>
            <Button onClick = {() => setShow(true)}> add </Button>
            <br/>
          </div>
        )
      }
      </div>
    </div>
  );
};
export default EventParticipants;
