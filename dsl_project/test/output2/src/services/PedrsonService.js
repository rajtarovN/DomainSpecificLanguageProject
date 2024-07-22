import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/pedrson/',
};

class PedrsonService {
    getCustomersByPedrson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByPedrson/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getPedrson = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOnePedrson = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createPedrson = async (pedrson) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                pedrson
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updatePedrson = async (pedrson, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                pedrson
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deletePedrson = async (id) => {
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

const pedrsonService = new PedrsonService();
export default pedrsonService;