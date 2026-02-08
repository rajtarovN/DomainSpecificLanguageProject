import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from './auth.service';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/bill/',
};

class BillService {
    getCustomersByBill = async (id) => {
    const headers = AuthService.authHeader(false);
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByBill/"+id,
                {headers: headers}
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getBill = async () => {
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
    getOneBill = async (id) => {
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
    createBill = async (bill) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                bill
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateBill = async (bill, id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                bill
                ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteBill = async (id) => {
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
    makeBill = async (basket) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post( 
                ENDPOINTS.BASE,
                basket ,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to make bill element.');
            return error;
        }
    };
    makeBillWithId = async (id) => {
        const headers = AuthService.authHeader(false);
        try {
            return await axios.post(
                ENDPOINTS.BASE + `make-with-id/${id}`,{headers: headers}
            );
        } catch (error) {
        toast.error('Failed to make bill element.');
            return error;
        }
    };

    
}

const billService = new BillService();
export default billService;