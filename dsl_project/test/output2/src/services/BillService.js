import axios from 'axios';
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
            return error;
        }
    };
    getBill = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneBill = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
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
            return error;
        }
    };
    deleteBill = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    makeBill = async (basket) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                basket
            );
        } catch (error) {
            return error;
        }
    };
    makeBillWithId = async (id) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE + `make-with-id/${id}`,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const billService = new BillService();
export default billService;