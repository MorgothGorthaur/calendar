import React, {useState, useEffect} from 'react';
import {Form, Button} from 'react-bootstrap';
import Input from '../UI/Input/Input';
import EventService from '../API/EventService';
const EventForm = ({createOrUpdate, event,id}) => {
  const[description, setDescription] = useState('');
  const[startTime, setStartTime] = useState();
  const[endTime, setEndTime] = useState();


  const updateNewEvent = (e) => {
    e.preventDefault();
    const data = EventService.change(event.id, description, startTime, endTime).then(data => {
      validation(data);
    })
  };
  const addNewEvent = (e) => {
    e.preventDefault();
    const data = EventService.save(id,description, startTime, endTime).then(data => {
      validation(data);
    })
  };

  const validation = (data) => {
    data.errors ? alert(data.errors) : createOrUpdate(data);
  }
  useEffect ( () => {
    if(event){
      setDescription(event.description);
      setStartTime(event.startTime);
      setEndTime(event.endTime);
    }
  },[event]);

  return (
    <Form className = "form" onSubmit = {event ? updateNewEvent : addNewEvent}>
        <Input type = "text" placeholder = "description" value = {description} onChange = {e => setDescription(e.target.value)}/>
        <Input type = "datetime-local" placeholder = "first time" value = {startTime} onChange = {e => setStartTime(e.target.value)}/>
        <Input type = "datetime-local" placeholder = "second time" value = {endTime} onChange = {e => setEndTime(e.target.value)}/>
        <Button type = "submit" > { event ? "update" : "create"} </Button>
    </Form>
  )
}
export default EventForm;
