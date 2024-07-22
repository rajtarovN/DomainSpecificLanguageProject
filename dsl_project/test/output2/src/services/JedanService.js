import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/jedan/',
};

class JedanService {
    getCustomersByJedan = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByJedan/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getJedan = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneJedan = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createJedan = async (jedan) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                jedan
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateJedan = async (jedan, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                jedan
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteJedan = async (id) => {
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

const jedanService = new JedanService();
export default jedanService;