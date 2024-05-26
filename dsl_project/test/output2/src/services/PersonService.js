import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/person/',
};

class PersonService {
    getPersonsByPerson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByPerson/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getPerson = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOnePerson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createPerson = async (person) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                person
            );
        } catch (error) {
            return error;
        }
    };
    updatePerson = async (person, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                person
            );
        } catch (error) {
            return error;
        }
    };
    deletePerson = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const personService = new PersonService();
export default personService;