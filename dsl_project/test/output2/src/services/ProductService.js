import axios from 'axios';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/product/',
};

class ProductService {
    getPersonsByProduct = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getPersonsByProduct/"+id,
            );
        } catch (error) {
            return error;
        }
    };
    getProduct = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
            return error;
        }
    };
    getOneProduct = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
            return error;
        }
    };
    createProduct = async (product) => {
        try {
            return await axios.post(
                ENDPOINTS.BASE,
                product
            );
        } catch (error) {
            return error;
        }
    };
    updateProduct = async (product, id) => {
        try {
            return await axios.put(
                ENDPOINTS.BASE+id,
                product
            );
        } catch (error) {
            return error;
        }
    };
    deleteProduct = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
            return error;
        }
    };
    
}

const productService = new ProductService();
export default productService;