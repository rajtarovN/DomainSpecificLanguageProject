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
import customerService from '../../services/CustomerService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import billService from '../../services/BillService';
import basketService from '../../services/BasketService';

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

const EditCustomer = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
  });
  const { id } = useParams();
  const [currentBill, setCurrentBill] = useState([]);
  const [allBill, setAllBill] = useState([]);
  const [baskets, setBaskets] = useState([]);
  const [selectedBasket, setSelectedBasket] = useState('');


  useEffect(() => {


  const fetchDataBasket = async () => {
      try {
          const response= await basketService.getBasket();
          if (response.status === 200) {
            setBaskets(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataBasket();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await customerService.getOneCustomer(id);
            if (response.status === 200) {
              setFormData({
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
    const fetchDataBill = async () => {
      try {
          const response2 = await billService.getBill();
          if (response2.status === 200) {
            setAllBill(response2.data);
        }
      } catch (error) {
       toast.error('Failed to create item. Please try again.');
          console.error(error);
      }
  };
  fetchDataBill();
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

              let liBill = [];
              for (let a of currentBill){
                liBill.push(a.id);
              }
              formData['billIds'] = liBill;
              for(let j in baskets){
                if (parseInt(selectedBasket)===parseInt(baskets[j].id)){
                  formData['basket'] = baskets[j];
                  break;
                }
              }
              const response = await customerService.updateCustomer(formData, id);
              if (response.status === 200) {
               toast.success('Item created successfully!');
                navigate(`/table-customer`);
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
              let li1 = [];
              for (let a of currentBill){
                li1.push(a.id);
              }
              formData['billIds'] = li1;
              for(let j in baskets){
                if (parseInt(selectedBasket)===parseInt(baskets[j].id)){ //todo ovde verujem da ide name
                  formData['basket'] = baskets[j];
                  break;
                }
              }
          const response = await customerService.createCustomer(formData);
          if (response.status === 200) {
           toast.success('Item created successfully!');
              navigate(`/table-customer`);
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

  const handleRemoveBill = (id) => {
    if (!currentBill || !allBill) return;

    for (const item of currentBill) {
      if (item.id === id) {
        const updatedCurrentBill = currentBill.filter(billItem => billItem.id !== id);
        setCurrentBill(updatedCurrentBill);
        const updatedAllBill = [...allBill, item];
        setAllBill(updatedAllBill);

        break;
      }
    }
  }


  const handleAddBill = (id) => {
    if (!currentBill || !allBill) return;

    for (const item of allBill) {
      if (item.id === id) {
        const updatedAllBill = allBill.filter(billItem => billItem.id !== id);
        setAllBill(updatedAllBill);
        const updatedCurrentBill = [...currentBill, item];
        setCurrentBill(updatedCurrentBill);

        break;
      }
    }
  }
    const handleChangeBasket = (event) => {
      console.log(event.target.value)
        setSelectedBasket(event.target.value);
    };


  return (
  <div>
    <ToastContainer />
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>




        Bill
        <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Add</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {currentBill.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemoveBill(item.id)}>Remove</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer><br/>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Remove</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allBill.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleAddBill(item.id)}>Add</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <div>
            <label htmlFor="dvaSelect">Select Basket: </label>
            <NativeSelect id="dvaSelect" value={selectedBasket} onChange={handleChangeBasket}>
                <option value="">--Please choose an option--</option>{
                 baskets.map(basket => (
                    <option  key={
                    basket.id} value={
                    basket.id}>
                        {
                        basket.id}
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

export default EditCustomer;