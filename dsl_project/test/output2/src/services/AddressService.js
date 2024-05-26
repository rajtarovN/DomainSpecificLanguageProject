import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/address/',
};

class AddressService {
    getPersonsByAddress = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByAddress/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getAddress = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneAddress = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createAddress = async (address) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                address
            );
        } catch (error) {
            return error;
        }
    };
    updateAddress = async (address, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                address
            );
        } catch (error) {
            return error;
        }
    };
    deleteAddress = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const addressService = new AddressService();
export default addressService;