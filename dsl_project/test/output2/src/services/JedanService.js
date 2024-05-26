import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/jedan/',
};

class JedanService {
    getPersonsByJedan = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByJedan/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getJedan = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneJedan = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createJedan = async (jedan) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                jedan
            );
        } catch (error) {
            return error;
        }
    };
    updateJedan = async (jedan, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                jedan
            );
        } catch (error) {
            return error;
        }
    };
    deleteJedan = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const jedanService = new JedanService();
export default jedanService;