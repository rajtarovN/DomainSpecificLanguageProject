import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from './auth.service';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/admin/',
};

class AdminService {
    getCustomersByAdmin = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByAdmin/"+id,
                {headers: headers}
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getAdmin = async () => {
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
    getOneAdmin = async (id) => {
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
    createAdmin = async (admin) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                admin
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateAdmin = async (admin, id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                admin
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteAdmin = async (id) => {
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

const adminService = new AdminService();
export default adminService;