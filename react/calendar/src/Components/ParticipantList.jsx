import React, {useState, useEffect} from 'react';
import ParticipantService from '../API/ParticipantService';
import {Button, Modal} from 'react-bootstrap';
import ParticipantForm from './ParticipantForm';
import Loader from '../UI/Loader/Loader';

const ParticipantList = () => {
    const [participants, setParticipants] = useState([]);
    const [modal, setModal] = useState(false);
    const [loading, setLoading] = useState(false);
    const add = (participant) => {
        setParticipants([...participants, participant]);
        setModal(false);
    }
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            fetchParticipants();
            setLoading(false);
        }, 1000);
    }, []);

    async function fetchParticipants() {
        const response = await ParticipantService.getAll();
        setParticipants(response);
    }
    return (
        <div>
            {loading ? (
                <div style={{display: 'flex', justifyContent: 'center'}}>
                    <Loader/>
                </div>
            ) : (
                <div className="participant_list">
                    {participants.length ? (
                        <div>
                            {participants.map(participant =>
                                <div className="participant_item">
                                    <h1> {participant.firstName} </h1>
                                    <h1> {participant.lastName} </h1>
                                </div>
                            )}
                        </div>
                    ) : (
                        <h1> participants not found! </h1>
                    )}
                    <Button onClick={() => setModal(true)}> add </Button>
                    <Modal show={modal} onHide={setModal}> <ParticipantForm CreateOrUpdate={add}/> </Modal>
                </div>
            )}
        </div>


    );
};
export default ParticipantList;
