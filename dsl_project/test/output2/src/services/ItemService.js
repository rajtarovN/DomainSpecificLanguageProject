import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/item/',
};

class ItemService {
    getPersonsByItem = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByItem/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getItem = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneItem = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createItem = async (item) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                item
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateItem = async (item, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                item
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteItem = async (id) => {
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

const itemService = new ItemService();
export default itemService;