import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/customer/',
};

class CustomerService {
    getCustomersByCustomer = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByCustomer/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getCustomer = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneCustomer = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createCustomer = async (customer) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                customer
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateCustomer = async (customer, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                customer
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteCustomer = async (id) => {
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

const customerService = new CustomerService();
export default customerService;