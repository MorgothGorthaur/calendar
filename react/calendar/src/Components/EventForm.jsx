import React, {useState, useEffect} from 'react';
import {Form, Button} from 'react-bootstrap';
import Input from '../UI/Input/Input';
import EventService from '../API/EventService';
const EventForm = ({tokens, event, CreateOrUpdate}) => {
  const [id, setId] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [description, setDescription] = useState('');
  const update = (e) => {
    e.preventDefault();
    EventService.change(tokens, id, description, startTime, endTime).then(data => {
      validation(data);
    })
  };

  const add = (e) => {
    e.preventDefault();
    EventService.save(tokens, description, startTime, endTime).then(data => {
      validation(data);
    })
  };

  const validation = (data) => {
    data.errors ? alert(data.errors) : CreateOrUpdate(data);
  };

  useEffect ( () => {
    if(event) {
      setId (event.id)
      setDescription (event.description)
      setStartTime (event.startTime)
      setEndTime (event.endTime)
    }
  }, []);

  return (
    <Form className = "form" onSubmit = {event ? update : add}>
      <Input type = "datetime-local" placeholder = "startTime" value = {startTime} onChange = {e => setStartTime(e.target.value)} />
      <Input type = "datetime-local" placeholder = "endTime" value = {endTime} onChange = {e => setEndTime(e.target.value)} />
      <Input type = "text" placeholder = "description" value = {description} onChange = {e => setDescription(e.target.value)} />
      <Button type = "submit"> {event ? "update" : "create"} </Button>
    </Form>
  );
};
export default EventForm;
