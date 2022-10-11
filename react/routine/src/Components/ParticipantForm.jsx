import React, {useState, useEffect} from 'react';
import {Button, Form} from 'react-bootstrap';
import ParticipantService from '../API/ParticipantService';
import Input from '../UI/Input/Input'
const ParticipantForm = ({createOrUpdate,participant}) => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const addNewParticipant = () => {

  };
  const updateNewParticipant = (e) => {
    e.preventDefault();
    const data = ParticipantService.save(firstName, lastName).then(data => {
      createOrUpdate(data);
    })
  };
  return (
    <Form onSubmit = {participant ? addNewParticipant : updateNewParticipant}>
      <Input type = "text" placeholder = "first name" value = {firstName} onChange = {e => setFirstName(e.target.value)}/>
      <Input type = "text" placeholder = "second name" value = {lastName} onChange = {e => setLastName(e.target.value)}/>
      <Button type = "submit" > {participant ? "update" : "create"} </Button>
    </Form>
  )
};
export default ParticipantForm;
