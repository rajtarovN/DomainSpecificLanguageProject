import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/szagor/',
};

class SzagorService {
    getCustomersBySzagor = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersBySzagor/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getSzagor = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneSzagor = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createSzagor = async (szagor) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                szagor
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateSzagor = async (szagor, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                szagor
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteSzagor = async (id) => {
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

const szagorService = new SzagorService();
export default szagorService;