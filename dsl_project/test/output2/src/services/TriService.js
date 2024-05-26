import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/tri/',
};

class TriService {
    getPersonsByTri = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByTri/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getTri = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneTri = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createTri = async (tri) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                tri
            );
        } catch (error) {
            return error;
        }
    };
    updateTri = async (tri, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                tri
            );
        } catch (error) {
            return error;
        }
    };
    deleteTri = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const triService = new TriService();
export default triService;