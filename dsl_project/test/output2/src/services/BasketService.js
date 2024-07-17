import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/basket/',
};

class BasketService {
    getPersonsByBasket = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByBasket/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getBasket = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneBasket = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    createBasket = async (basket) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                basket
            );
        } catch (error) {
        toast.error('Failed to create element.');
            return error;
        }
    };
    updateBasket = async (basket, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                basket
            );
        } catch (error) {
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteBasket = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
        toast.error('Failed to delete element.');
            return error;
        }
    };
    addItemToBasket= async (basket_id, item_id, quantity) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+basket_id+"/"+item_id+"/"+quantity, //todo
            );
        } catch (error) {
        toast.error('Failed to add item to basket element.');
            return error;
        }
    };
    removeItem = async (basketId, itemId) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+basketId+"/"+itemId, //todo
            );
        } catch (error) {
        toast.error('Failed to remove item from basket element.');
            return error;
        }
    }
    
}

const basketService = new BasketService();
export default basketService;