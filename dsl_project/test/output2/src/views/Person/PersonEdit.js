import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useParams } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import personService from '../../services/PersonService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

import addressService from '../../services/AddressService';
import productService from '../../services/ProductService';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-start',
    width: '100%',
    marginLeft: '50px',
    marginRight: 'auto',
  },
  formGroup: {
    marginBottom: theme.spacing(2),
  },
  buttonGroup: {
    marginTop: theme.spacing(2),
  },
}));

const EditPerson = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    age: '',
  });
  const { id } = useParams();
  const [addresss, setAddresss] = useState([]);
  const [selectedAddress, setSelectedAddress] = useState('');

  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState('');


  useEffect(() => {
  const fetchDataAddress = async () => {
      try {
          const response= await addressService.getAddress();
          if (response.status === 200) {
            setAddresss(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataAddress();
  const fetchDataProduct = async () => {
      try {
          const response= await productService.getProduct();
          if (response.status === 200) {
            setProducts(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataProduct();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await personService.getOnePerson(id);
            if (response.status === 200) {
              setFormData({
                 age: response.data.age,

              })
            }
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
      console.log('Component unmounted');
    };}
  }, [id]);


  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const navigate = useNavigate();
  const handleSubmit = (e) => {
    if (id!=null){
        e.preventDefault();
        const fetchData = async () => {
          try {
              for(let j in addresss){
                if (parseInt(selectedAddress)===parseInt(addresss[j].id)){
                  formData['address'] = addresss[j];
                  break;
                }
              }
              for(let j in products){
                if (parseInt(selectedProduct)===parseInt(products[j].id)){
                  formData['product'] = products[j];
                  break;
                }
              }
              const response = await personService.updatePerson(formData, id);
              if (response.status === 200) {
                navigate(`/table-person`);
              }
          } catch (error) {
              console.error(error);
          }
      };
      fetchData();
    }
  else{
    e.preventDefault();
    console.log(formData);
    const fetchData = async () => {
      try {
              for(let j in addresss){
                if (parseInt(selectedAddress)===parseInt(addresss[j].id)){ //todo ovde verujem da ide name
                  formData['address'] = addresss[j];
                  break;
                }
              }
              for(let j in products){
                if (parseInt(selectedProduct)===parseInt(products[j].id)){ //todo ovde verujem da ide name
                  formData['product'] = products[j];
                  break;
                }
              }
          const response = await personService.createPerson(formData);
          if (response.status === 200) {
              navigate(`/table-person`);
          }
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  }
  };

  const handleCancel = () => {
    console.log('Form canceled');
  };

    const handleChangeAddress = (event) => {
      console.log(event.target.value)
        setSelectedAddress(event.target.value);
    };
    const handleChangeProduct = (event) => {
      console.log(event.target.value)
        setSelectedProduct(event.target.value);
    };



  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="age"
          name="age"
          value={formData.age}
          type="number"
          onChange={handleChange}
          variant="outlined"
        />
      </div>




      <div>
            <label htmlFor="dvaSelect">Select Address: </label>
            <NativeSelect id="dvaSelect" value={selectedAddress} onChange={handleChangeAddress}>
                <option value="">--Please choose an option--</option>{
                 addresss.map(address => (
                    <option  key={
                    address.id} value={
                    address.id}>
                        {
                        address.id}
                    </option >
                ))}
            </NativeSelect>
        </div>


      <div>
            <label htmlFor="dvaSelect">Select Product: </label>
            <NativeSelect id="dvaSelect" value={selectedProduct} onChange={handleChangeProduct}>
                <option value="">--Please choose an option--</option>{
                 products.map(product => (
                    <option  key={
                    product.id} value={
                    product.id}>
                        {
                        product.id}
                    </option >
                ))}
            </NativeSelect>
        </div>



      <div className={classes.buttonGroup}>
        <Button variant="contained" color="primary" type="submit">
          Submit
        </Button>
        <Button variant="contained" color="secondary" onClick={handleCancel}>
          Cancel
        </Button>
      </div>
    </form>
  );
};

export default EditPerson;