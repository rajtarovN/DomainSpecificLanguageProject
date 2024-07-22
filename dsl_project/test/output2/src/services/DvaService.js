import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/dva/',
};

class DvaService {
    getCustomersByDva = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByDva/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getDva = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneDva = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createDva = async (dva) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                dva
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateDva = async (dva, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                dva
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteDva = async (id) => {
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

const dvaService = new DvaService();
export default dvaService;