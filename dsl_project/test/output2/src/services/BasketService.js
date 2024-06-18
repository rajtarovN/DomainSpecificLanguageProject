import axios from 'axios';
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
            return error;
        }
    };
    getBasket = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneBasket = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
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
            return error;
        }
    };
    deleteBasket = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    addItemToBasket= async (basket_id, item_id, quantity) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+basket_id+"/"+item_id+"/"+quantity,
            );
        } catch (error) {
            return error;
        }
    };
    removeItem = async (basketId, itemId) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+basketId+"/"+itemId,
            );
        } catch (error) {
            return error;
        }
    }
    
}

const basketService = new BasketService();
export default basketService;