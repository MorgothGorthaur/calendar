/*import axios from "axios";*/
import React, {useState} from 'react';
export default class DaysService {
  static async getAll(){
    /*const response = await axios.get('http://localhost:8080/routine');
    return response.data;*/
    try{
	const response = await fetch('http://localhost:8080/routine');
	const data = await response.json();
	return data;
    } catch (e) {
	alert(e);
    }
  }

  static async addDay(name, date){
    var pad = function(num) { return ('00'+num).slice(-2) };

    /*date = date.getFullYear()         + '-' +
        pad(date.getUTCMonth() + 1)  + '-' +
        pad(date.getUTCDate());

    console.log(date);*/
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({name, date})
    };

    const response = await fetch('http://localhost:8080/routine', requestOptions);
    const data = await response.json();
    return data;
  }
  static async changeDay(id, name, date){
    const requestOptions = {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({id, name, date})
    };

    const response = await fetch('http://localhost:8080/routine', requestOptions);
    const data = await response.json();
    return data;


  }
  static async removeDay(id){
    const requestOptions = {
        method: 'DELETE',
    };

    const response = await fetch('http://localhost:8080/routine/' + id, requestOptions);
    const data = await response.json();
    alert(data);
    return data;


  }
}
