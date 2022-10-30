import React, {useState, useEffect} from 'react';
import {Button, Form} from 'react-bootstrap';
import ParticipantService from '../API/ParticipantService';
import Input from '../UI/Input/Input'
const ParticipantForm = ({createOrUpdate,participant}) => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const updateNewParticipant = (e) => {
    e.preventDefault();
    ParticipantService.change(participant.id, firstName, lastName).then(data => {
      validation(data);
    })
  };
  const addNewParticipant = (e) => {
    e.preventDefault();
    ParticipantService.save(firstName, lastName).then(data => {
      validation(data);
    })
  };
  const validation = (data) => {
    data.errors ? alert(data.errors) : createOrUpdate(data);
  }
  useEffect ( () => {
    if(participant){
      setFirstName(participant.firstName);
      setLastName(participant.lastName);
    }
  },[participant]);
  return (
    <Form className = "form" onSubmit = {participant ? updateNewParticipant : addNewParticipant}>
      <Input type = "text" placeholder = "first name" value = {firstName} onChange = {e => setFirstName(e.target.value)}/>
      <Input type = "text" placeholder = "second name" value = {lastName} onChange = {e => setLastName(e.target.value)}/>
      <Button type = "submit" > {participant ? "update" : "create"} </Button>
    </Form>
  )
};
export default ParticipantForm;
