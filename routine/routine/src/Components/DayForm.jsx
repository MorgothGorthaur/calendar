import React, {useState, useEffect}  from 'react';
import {Form, Button} from 'react-bootstrap'

import Input from '../UI/Input/Input';
import DaysService from '../API/DaysService';
const DayForm = ({createOrUpdate,day}) => {
  const [name, setName] = useState("");
  const [date, setDate] = useState();
  const [id, setId] = useState(0);
  const validation = (data) => {
    if (data.errors){
      alert(data.errors)
    } else {
      const newDay = {id: data.id, name, date};
      createOrUpdate(newDay);
    }
  }
  const addNewDay = (e) =>{
      e.preventDefault();
      if(!day){
        DaysService.addDay(name, date).then(data => {
          validation(data)
        }
      )
    } else {
      DaysService.changeDay(id, name, date).then(data => {
        validation(data)
      }
    )
    }


  }

  useEffect( () =>{
    if (day){
      setName(day.name)
      setDate(day.date)
      setId(day.id)
    }
  },[day])
  return (

      <Form onSubmit = {addNewDay}>
        <Input type = "text" placeholder= "day Name" value = {name} onChange = { e => setName(e.target.value)}/>
        <Input type = "date" placeholed = "day date" value = {date} onChange = {e => setDate(e.target.value)}/>

        <Button type = "submit"> {day ?  "update" : "create"} </Button>
      </Form>
    )
  };
export default DayForm;
