import React, {useState, useEffect} from 'react';
import EventService from '../API/EventService';
import {Button} from 'react-bootstrap';
import EmailForm from './EmailForm';
const EventParticipants = ({tokens, id}) => {
  const [participants, setParticipants] = useState([]);
  const [show, setShow] = useState(false);
  useEffect ( () => {
    fetchParticipants();
  }, []);

  async function fetchParticipants () {
      const response = await EventService.getParticipants(tokens, id);
      setParticipants(response);
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
          <EmailForm tokens = {tokens} id = {id} update = {update}/>
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
