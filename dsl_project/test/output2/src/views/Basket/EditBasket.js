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
import basketService from '../../services/BasketService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import customerService from '../../services/CustomerService';

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
    margin: theme.spacing(2),
  },
  buttonGroup: {
    marginTop: theme.spacing(2),
  },
  textField: {
    '& label': {
      transform: 'translate(14px, -6px) scale(0.75)',
    },
    '& input': {
      padding: '18.5px 14px',
    },
  },
}));

const EditBasket = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    formular: '',
  });
  const { id } = useParams();
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState('');


  useEffect(() => {


  const fetchDataCustomer = async () => {
      try {
          const response= await customerService.getCustomer();
          if (response.status === 200) {
            setCustomers(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataCustomer();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await basketService.getOneBasket(id);
            if (response.status === 200) {
              setFormData({
                 formular: response.data.formular,

              })
            }
        } catch (error) {
         toast.error('Failed to create item. Please try again.');
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
              for(let j in customers){
                if (parseInt(selectedCustomer)===parseInt(customers[j].id)){
                  formData['customer'] = customers[j];
                  break;
                }
              }
              const response = await basketService.updateBasket(formData, id);
              if (response.status === 200) {
               toast.success('Item created successfully!');
                navigate(`/table-basket`);
              }
          } catch (error) {
           toast.error('Failed to create item. Please try again.');
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
              for(let j in customers){
                if (parseInt(selectedCustomer)===parseInt(customers[j].id)){ //todo ovde verujem da ide name
                  formData['customer'] = customers[j];
                  break;
                }
              }
          const response = await basketService.createBasket(formData);
          if (response.status === 200) {
           toast.success('Item created successfully!');
              navigate(`/table-basket`);
          }
      } catch (error) {
       toast.error('Failed to create item. Please try again.');
          console.error(error);
      }
  };
  fetchData();
  }
  };

  const handleCancel = () => {
    console.log('Form canceled');
  };

    const handleChangeCustomer = (event) => {
      console.log(event.target.value)
        setSelectedCustomer(event.target.value);
    };


  return (
  <div>
    <ToastContainer />
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="formular"
          name="formular"
          value={formData.formular}
          onChange={handleChange}
          variant="outlined"
        />
      </div>




      <div>
            <label htmlFor="dvaSelect">Select Customer: </label>
            <NativeSelect id="dvaSelect" value={selectedCustomer} onChange={handleChangeCustomer}>
                <option value="">--Please choose an option--</option>{
                 customers.map(customer => (
                    <option  key={
                    customer.id} value={
                    customer.id}>
                        {
                        customer.id}
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
    </div>
  );
};

export default EditBasket;