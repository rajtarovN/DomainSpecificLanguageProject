import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/itemWithPrice/',
};

class ItemWithPriceService {
    getCustomersByItemWithPrice = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByItemWithPrice/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getItemWithPrice = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneItemWithPrice = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createItemWithPrice = async (itemWithPrice) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                itemWithPrice
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateItemWithPrice = async (itemWithPrice, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                itemWithPrice
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteItemWithPrice = async (id) => {
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

const itemWithPriceService = new ItemWithPriceService();
export default itemWithPriceService;