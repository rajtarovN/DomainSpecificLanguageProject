import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from './auth.service';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/address/',
};

class AddressService {
    getCustomersByAddress = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByAddress/"+id,
                {headers: headers}
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getAddress = async () => {
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
    getOneAddress = async (id) => {
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
    createAddress = async (address) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                address
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAddress = async (address, id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                address
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAddress = async (id) => {
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

const addressService = new AddressService();
export default addressService;