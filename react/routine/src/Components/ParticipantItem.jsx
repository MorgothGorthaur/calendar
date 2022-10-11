import React, {useState} from 'react';
import EventList from './EventList';
import {Button,Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
const ParticipantItem = ({participant}) => {
  const[show, setShow] = useState(false);
  const[modal, setModal] = useState(false);
  return (
    <div>
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <div>
        {show ?(
          <div>
            <div>
              <EventList id = {participant.id} />
            </div>
            <div>
              <Button variant = "dark" onClick = {() => setShow(false)}> close </Button>
              <Button variant = "primary" onClick = {() => setModal(true)}> change </Button>
              <Modal show = {modal} onHide = {setModal}> </Modal>
            </div>
          </div>
        ):(
            <Button variant = "primary" onClick = {() => setShow(true)}> open </Button>
        )}
      </div>
    </div>
  )
}
export default ParticipantItem;
