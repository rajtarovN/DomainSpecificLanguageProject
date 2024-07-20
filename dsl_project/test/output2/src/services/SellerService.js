import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/seller/',
};

class SellerService {
    getCustomersBySeller = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersBySeller/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getSeller = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneSeller = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createSeller = async (seller) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                seller
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateSeller = async (seller, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                seller
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteSeller = async (id) => {
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

const sellerService = new SellerService();
export default sellerService;