import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/szagor/',
};

class SzagorService {
    getPersonsBySzagor = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsBySzagor/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getSzagor = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneSzagor = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createSzagor = async (szagor) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                szagor
            );
        } catch (error) {
            return error;
        }
    };
    updateSzagor = async (szagor, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                szagor
            );
        } catch (error) {
            return error;
        }
    };
    deleteSzagor = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const szagorService = new SzagorService();
export default szagorService;