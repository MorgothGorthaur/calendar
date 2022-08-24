import React, {useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import DayForm from './DayForm';
import EventsList from './EventsList';
const FullDay = ({day, update}) =>{
  const [modal, setModal] = useState(false);
  const updateDay = (newDay) =>{
    setModal(false)
    update(newDay);
  };

  return (
    <div className = "day_item">
      <h1  style = {{textAlign: 'center'}}>{day.name} </h1>
      <h3  style = {{textAlign: 'right'}}> {day.date} </h3>
      <hr/>
      <div style = {{textAlign: 'right'}} >
        <Button onClick = { () => setModal(true)}> change day </Button>
        <EventsList /*events = {sortedEvents}  remove = {removeEvent} change = {changeEvent} add = {addNewEvent}*/ dayId = {day.id}/>
        <Modal show = {modal} onHide = {setModal} className = "subModal"> <DayForm createOrUpdate = {updateDay} day = {day}/> </Modal>
      </div>
    </div>
  )
};
export default FullDay;
