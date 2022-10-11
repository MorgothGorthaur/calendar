import React, {useState, useEffect} from 'react';
import EventList from './EventList';
import {Button} from 'react-bootstrap';
const ParticipantItem = ({participant}) => {
  const[show, setShow] = useState(false);
  return (
    <div>
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <div>
        <Button variant = "primary" onClick = {() => setShow(true)}> open </Button>
        {show ?(
          <div>
            <div>
              <EventList id = {participant.id} />
            </div>
            <Button variant = "dark" onClick = {() => setShow(false)}> close </Button>
          </div>
        ):(<hr/>)}
      </div>
    </div>
  )
}
export default ParticipantItem;
