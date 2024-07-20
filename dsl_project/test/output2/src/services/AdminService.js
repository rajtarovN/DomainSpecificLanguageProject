import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/admin/',
};

class AdminService {
    getCustomersByAdmin = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByAdmin/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getAdmin = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneAdmin = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createAdmin = async (admin) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                admin
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAdmin = async (admin, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                admin
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAdmin = async (id) => {
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

const adminService = new AdminService();
export default adminService;