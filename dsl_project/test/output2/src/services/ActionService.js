import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/action/',
};

class ActionService {
    getPersonsByAction = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByAction/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getAction = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneAction = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createAction = async (action) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                action
            );
        } catch (error) {
            return error;
        }
    };
    updateAction = async (action, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                action
            );
        } catch (error) {
            return error;
        }
    };
    deleteAction = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const actionService = new ActionService();
export default actionService;