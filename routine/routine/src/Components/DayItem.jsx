import React, {useState} from 'react';
import { Button, Modal} from 'react-bootstrap';
import FullDay from './FullDay'

const DayItem = ({day, remove, update} ) => {
  const [fullDayView, setFullDayView] = useState(false);

  return (

    <div className = "day_item">
      <h1 style = {{textAlign: 'center'}}> {day.name} </h1>
      <h3 style = {{textAlign: 'right'}} > {day.date} </h3>
      <hr/>
      <div style = {{textAlign: 'right'}}>
        <Button onClick = { () => /*fetchEvents()*/ setFullDayView(true)}> open </Button>
        <Button onClick = { () => {remove(day)}} variant="danger"> remove </Button>
        <Modal className = "modal" show = {fullDayView} onHide = {setFullDayView}> <FullDay day = {day} /*events = {sortedEvents}*/ update = {update} /*setEvents = {setEvents}*/ / ></Modal>
      </div>
    </div>
  )
};
export default DayItem;
