import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/bill/',
};

class BillService {
    getPersonsByBill = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByBill/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getBill = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneBill = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createBill = async (bill) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                bill
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateBill = async (bill, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                bill
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteBill = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
        toast.error('Failed to delete element.');
            return error;
        }
    };
    makeBill = async (basket) => {
        try {
            return await axios.post( //todo
                ENDPOINTS.BASE,
                basket
            );
        } catch (error) {
        toast.error('Failed to make bill element.');
            return error;
        }
    };
    makeBillWithId = async (id) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE + `make-with-id/${id}`, //todo
            );
        } catch (error) {
        toast.error('Failed to make bill element.');
            return error;
        }
    };
    
}

const billService = new BillService();
export default billService;