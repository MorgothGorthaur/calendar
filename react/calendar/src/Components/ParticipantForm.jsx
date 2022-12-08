import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import Input from '../UI/Input/Input';
import {Button, Form} from 'react-bootstrap';
const ParticipantForm = ({participant, CreateOrUpdate}) => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  useEffect ( () => {
    if(participant){
      setFirstName(participant.firstName);
      setLastName(participant.lastName);
      setEmail(participant.email);
    }
  },[participant]);

  const update = (e) => {
    e.preventDefault();
  };

  const add = (e) => {
    e.preventDefault();
    ParticipantService.save(firstName, lastName, email, password).then(data => {
      validation(data);
    });
  };

  const validation = (data) => {
    data.errors ? alert(data.errors) : CreateOrUpdate(data);
  }

  return (
    <Form className = "form" onSubmit = {participant ? update : add}>
      <Input type = "text" placeholder = "firstName" value = {firstName} onChange = {e => setFirstName(e.target.value)} />
      <Input type = "text" placeholder = "lastName" value = {lastName} onChange = {e => setLastName(e.target.value)} />
      <Input type = "text" placeholder = "email" value = {email} onChange = {e => setEmail(e.target.value)} />
      <Input type = "password" placeholder = "password" value = {password} onChange = {e => setPassword(e.target.value)} />
      <Button type = "submit"> {participant ? "update" : "create"} </Button>
    </Form>
  );
};
export default ParticipantForm;
