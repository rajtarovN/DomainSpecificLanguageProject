import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/novv/',
};

class NovvService {
    getPersonsByNovv = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByNovv/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getNovv = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneNovv = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createNovv = async (novv) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                novv
            );
        } catch (error) {
            return error;
        }
    };
    updateNovv = async (novv, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                novv
            );
        } catch (error) {
            return error;
        }
    };
    deleteNovv = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const novvService = new NovvService();
export default novvService;