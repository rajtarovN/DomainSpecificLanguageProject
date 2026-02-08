import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from './auth.service';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/action/',
};

class ActionService {
    getCustomersByAction = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByAction/"+id,
                {headers: headers}
            );
        } catch (error) {
            return error;
        }
    };
    getAction = async () => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneAction = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createAction = async (action) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                action
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAction = async (action, id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                action
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAction = async (id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to delete element.');
            return error;
        }
    };
    
}

const actionService = new ActionService();
export default actionService;