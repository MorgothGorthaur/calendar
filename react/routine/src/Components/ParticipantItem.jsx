import React, {useState} from 'react';
import EventList from './EventList';
import {Button,Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
const ParticipantItem = ({participant, remove, change}) => {
  const[show, setShow] = useState(false);
  const[modal, setModal] = useState(false);
  const update = (data) =>{
    setModal(false);
    change(data);
  };
  return (
    <div>
      <h1> {participant.firstName} </h1>
      <h1> {participant.lastName} </h1>
      <div>
        {show ?(
          <div>
            <div>
              <EventList participantId = {participant.id} />
            </div>
            <div>
              <Button variant = "dark" onClick = {() => setShow(false)}> close </Button>
              <Button variant = "primary" onClick = {() => setModal(true)}> change </Button>
              <Modal show = {modal} onHide = {setModal}> <ParticipantForm participant = {participant} createOrUpdate = {update}/></Modal>
            </div>


          </div>
        ):(
          <div>
            <Button variant = "danger" onClick = {() => remove(participant.id)}> remove </Button>
            <Button variant = "primary" onClick = {() => setShow(true)}> open </Button>
          </div>
        )}
      </div>
    </div>
  )
}
export default ParticipantItem;
