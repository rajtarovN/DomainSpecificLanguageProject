import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/action/',
};

class ActionService {
    getPersonsByAction = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByAction/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getAction = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneAction = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createAction = async (action) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                action
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAction = async (action, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                action
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAction = async (id) => {
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

const actionService = new ActionService();
export default actionService;