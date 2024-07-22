import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const ENDPOINTS = {
    BASE: 'http://localhost:8080/api/product/',
};

class ProductService {
    getCustomersByProduct = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE+"getCustomersByProduct/"+id,
            );
        } catch (error) {
         toast.error('Failed to get element.');
            return error;
        }
    };
    getProduct = async () => {
        try {
            return await axios.get(
                ENDPOINTS.BASE,
            );
        } catch (error) {
        toast.error('Failed to get element.');
            return error;
        }
    };
    getOneProduct = async (id) => {
        try {
            return await axios.get(
                ENDPOINTS.BASE +id,
            );
        } catch (error) {
        toast.error('Failed to get element.');
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
        toast.error('Failed to create element.');
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
        toast.error('Failed to update element.');
            return error;
        }
    };
    deleteProduct = async (id) => {
        try {
            return await axios.delete(
                ENDPOINTS.BASE+id,
            );
        } catch (error) {
        toast.error('Failed to delete element.');
            return error;
        }
    };
    
}

const productService = new ProductService();
export default productService;