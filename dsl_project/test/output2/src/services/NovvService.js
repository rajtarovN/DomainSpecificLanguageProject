import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/novv/',
};

class NovvService {
    getCustomersByNovv = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByNovv/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getNovv = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneNovv = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createNovv = async (novv) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                novv
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateNovv = async (novv, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                novv
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteNovv = async (id) => {
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

const novvService = new NovvService();
export default novvService;