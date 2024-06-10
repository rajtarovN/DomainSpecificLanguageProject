import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/itemWithPrice/',
};

class ItemWithPriceService {
    getPersonsByItemWithPrice = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByItemWithPrice/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getItemWithPrice = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneItemWithPrice = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createItemWithPrice = async (itemWithPrice) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                itemWithPrice
            );
        } catch (error) {
            return error;
        }
    };
    updateItemWithPrice = async (itemWithPrice, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                itemWithPrice
            );
        } catch (error) {
            return error;
        }
    };
    deleteItemWithPrice = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const itemWithPriceService = new ItemWithPriceService();
export default itemWithPriceService;