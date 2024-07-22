import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/address/',
};

class AddressService {
    getCustomersByAddress = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByAddress/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getAddress = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneAddress = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createAddress = async (address) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                address
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAddress = async (address, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                address
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAddress = async (id) => {
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

const addressService = new AddressService();
export default addressService;