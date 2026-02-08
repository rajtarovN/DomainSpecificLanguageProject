import axios from 'axios';
const API_URL = 'http://localhost:8080/api/';

const login = (data) => {
    return axios
        .post('http://localhost:8080/api/login', data)
        .then((response) => { 
            if (response.data.token) {
                localStorage.setItem('user', JSON.stringify(response.data));
                
                console.log(JSON.parse(localStorage.getItem('user')));
            }
            return response.data;
        });
};

const register = (data) => {
    return axios
        .post('http://localhost:8080/auth/register', data)
        .then((response) => {
            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem('user');
    return axios.get(API_URL + 'logout');
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem('user'));
};

const authHeader = (withContentType) => {
    let token = JSON.parse(localStorage.getItem('user')).token;
    console.log(token);
    if (withContentType) {
        return {
            'Content-Type': 'application/json',
            'X-Auth-Token': token,
        };
    } else {
        return {
            'X-Auth-Token': token,
        };
    }
};

const AuthService = {
    login,
    logout,
    getCurrentUser,
    authHeader,
    register,
};

export default AuthService;