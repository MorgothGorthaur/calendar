import {Button, Form} from 'react-bootstrap';
import React, {useState} from 'react';
import Input from '../UI/Input/Input';
import EventService from '../API/EventService';
const EmailForm = ({tokens, id, update}) => {
  const [email, setEmail] = useState('');
  const add = (e) => {
    e.preventDefault();
    EventService.addParticipant(tokens, id, email).then(data => {validation(data)});
  };

  const validation = (data) => {
    data.errors ? alert(data.errors) : update(data);
  }
  return (
    <Form className = "form" onSubmit = {add}>
      <Input type = "text" placeholder = "email" value = {email} onChange = {e => setEmail(e.target.value)} />
      <Button type = "submit"> {"add"} </Button>
    </Form>
  );
};
export default EmailForm;
