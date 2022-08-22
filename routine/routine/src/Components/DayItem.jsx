import React, {useState, useEffect} from 'react';
import { Button, Modal} from 'react-bootstrap';

//import Modal from '../UI/Modal/Modal';
import FullDay from './FullDay'

import EventService from '../API/EventService';
const DayItem = ({day, remove, update} ) => {



const [fullDayView, setFullDayView] = useState(false);
const [events, setEvents] = useState([]);
const [sortedEvents, setSortedEvents] = useState([])
useEffect (()=>{
  setSortedEvents([...events].sort( (a,b) => new Date('1/1/1999 ' + a.date) - new Date('1/1/1999 ' + b.date)))

},[events])
async function fetchEvents() {
 const response = await EventService.getAll(day.id);
 setEvents(response.events)
 setFullDayView(true);
}



  return (

    <div className = "day_item">
      <h1 style = {{textAlign: 'center'}}> {day.name} </h1>
      <h3 style = {{textAlign: 'right'}} > {day.date} </h3>
      <hr/>
      <div style = {{textAlign: 'right'}}>
        <Button onClick = { () => fetchEvents()}> open </Button>
        <Button onClick = { () => {remove(day)}} variant="danger"> remove </Button>
        <Modal visible = {fullDayView} setVisible = {setFullDayView} show = {fullDayView} onHide = {setFullDayView}> <FullDay day = {day} events = {sortedEvents} update = {update} setEvents = {setEvents} / ></Modal>
      </div>
    </div>
  )
};
export default DayItem;
