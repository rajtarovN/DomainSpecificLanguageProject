import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/person/',
};

class PersonService {
    getCustomersByPerson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByPerson/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getPerson = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOnePerson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
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
        toast.error('Failed to create element.');
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
        toast.error('Failed to update element.');
            return error;
        }
    };
    deletePerson = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
        toast.error('Failed to delete element.');
            return error;
        }
    };
    
}

const personService = new PersonService();
export default personService;