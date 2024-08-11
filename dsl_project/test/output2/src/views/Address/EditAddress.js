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
import addressService from '../../services/AddressService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import billService from '../../services/BillService';

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

const EditAddress = () => {
 const [userType] = useState(
    JSON.parse(localStorage.getItem('user'))
        ? JSON.parse(localStorage.getItem('user')).userType
        : '');
  const classes = useStyles();
  const [formData, setFormData] = useState({
    street: '',
    number: '',
    zip: '',
  });
  const { id } = useParams();
  const [bills, setBills] = useState([]);
  const [selectedBill, setSelectedBill] = useState('');


  useEffect(() => {


  const fetchDataBill = async () => {
      try {
          const response= await billService.getBill();
          if (response.status === 200) {
            setBills(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataBill();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await addressService.getOneAddress(id);
            if (response.status === 200) {
              setFormData({
                 street: response.data.street,
                 number: response.data.number,
                 zip: response.data.zip,
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
              for(let j in bills){
                if (parseInt(selectedBill)===parseInt(bills[j].id)){
                  formData['bill'] = bills[j];
                  break;
                }
              }
              const response = await addressService.updateAddress(formData, id);
              if (response.status === 200) {
               toast.success('Item created successfully!');
                navigate(`/table-address`);
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
              for(let j in bills){
                if (parseInt(selectedBill)===parseInt(bills[j].id)){
                  formData['bill'] = bills[j];
                  break;
                }
              }
          const response = await addressService.createAddress(formData);
          if (response.status === 200) {
           toast.success('Item created successfully!');
              navigate(`/table-address`);
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

    const handleChangeBill = (event) => {
      console.log(event.target.value)
        setSelectedBill(event.target.value);
    };


  return (
  <div>
    <ToastContainer />
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="street"
          name="street"
          value={formData.street}
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <div className={classes.formGroup}>
        <TextField
          label="number"
          name="number"
          value={formData.number}
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <div className={classes.formGroup}>
        <TextField
          label="zip"
          name="zip"
          value={formData.zip}
          onChange={handleChange}
          variant="outlined"
        />
      </div>




      <div>
            <label htmlFor="dvaSelect">Select Bill: </label>
            <NativeSelect id="dvaSelect" value={selectedBill} onChange={handleChangeBill}>
                <option value="">--Please choose an option--</option>{
                 bills.map(bill => (
                    <option  key={
                    bill.id} value={
                    bill.id}>
                        {
                        bill.id}
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

export default EditAddress;