import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/dva/',
};

class DvaService {
    getPersonsByDva = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByDva/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getDva = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneDva = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createDva = async (dva) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                dva
            );
        } catch (error) {
            return error;
        }
    };
    updateDva = async (dva, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                dva
            );
        } catch (error) {
            return error;
        }
    };
    deleteDva = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const dvaService = new DvaService();
export default dvaService;