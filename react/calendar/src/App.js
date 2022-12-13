
import './App.css';
import React, {useState} from 'react';
import ParticipantList from './Components/ParticipantList';
import Participant from './Components/Participant';
import Login from './Components/Login';
import {Button, Modal} from 'react-bootstrap';
function App() {
  const [login, setLogin] = useState(false);
  const [modal, setModal] = useState(false);
  const [tokens, setTokens] = useState('');
  return (
    <div className="App">
      <header className = "App-header" >
        <Button variant = "dark"  onClick = {() => setLogin(true)}> login </Button>
        <Button variant = "dark"  onClick = {() => setModal(true)}> home </Button>
      </header>
      <ParticipantList />
      <Modal show = {login} onHide = {setLogin}>
        <Login setTokens = {setTokens} setModal = {setLogin}/>
      </Modal>
      <Modal show = {modal} onHide = {setModal}>
        <Participant tokens = {tokens} setTokens = {setTokens} setModal = {setModal} />
      </Modal>
    </div>
  );
}

export default App;
