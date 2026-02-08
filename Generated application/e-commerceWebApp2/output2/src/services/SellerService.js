import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from './auth.service';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/seller/',
};

class SellerService {
    getCustomersBySeller = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersBySeller/"+id,
                {headers: headers}
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getSeller = async () => {
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
    getOneSeller = async (id) => {
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
    createSeller = async (seller) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                seller
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateSeller = async (seller, id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                seller
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteSeller = async (id) => {
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

const sellerService = new SellerService();
export default sellerService;