import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/tri/',
};

class TriService {
    getCustomersByTri = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByTri/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getTri = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneTri = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createTri = async (tri) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                tri
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateTri = async (tri, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                tri
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteTri = async (id) => {
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

const triService = new TriService();
export default triService;