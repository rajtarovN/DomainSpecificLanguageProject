import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/item/',
};

class ItemService {
    getPersonsByItem = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByItem/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getItem = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneItem = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createItem = async (item) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                item
            );
        } catch (error) {
            return error;
        }
    };
    updateItem = async (item, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                item
            );
        } catch (error) {
            return error;
        }
    };
    deleteItem = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const itemService = new ItemService();
export default itemService;