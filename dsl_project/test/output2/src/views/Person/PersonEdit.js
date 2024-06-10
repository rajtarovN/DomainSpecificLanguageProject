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

import basketService from '../../services/BasketService';
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
  },
  buttonGroup: {
    marginTop: theme.spacing(2),
  },
}));

const EditPerson = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
    username: '',
  });
  const { id } = useParams();
  const [baskets, setBaskets] = useState([]);
  const [selectedBasket, setSelectedBasket] = useState('');

  const [currentBill, setCurrentBill] = useState([]);
  const [allBill, setAllBill] = useState([]);

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
            const response = await personService.getOnePerson(id);
            if (response.status === 200) {
              setFormData({
                 name: response.data.name,
                 lastName: response.data.lastName,
                 username: response.data.username,

              })
            }
        } catch (error) {
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
              for(let j in baskets){
                if (parseInt(selectedBasket)===parseInt(baskets[j].id)){
                  formData['basket'] = baskets[j];
                  break;
                }
              }

              let liBill = [];
              for (let a of currentBill){
                liBill.push(a.id);
              }
              formData['billIds'] = liBill;
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
              for(let j in baskets){
                if (parseInt(selectedBasket)===parseInt(baskets[j].id)){ //todo ovde verujem da ide name
                  formData['basket'] = baskets[j];
                  break;
                }
              }
              let li1 = [];
              for (let a of currentBill){
                li1.push(a.id);
              }
              formData['billIds'] = li1;
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

    const handleChangeBasket = (event) => {
      console.log(event.target.value)
        setSelectedBasket(event.target.value);
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



  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="name"
          name="name"
          value={formData.name}
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <div className={classes.formGroup}>
        <TextField
          label="lastName"
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <div className={classes.formGroup}>
        <TextField
          label="username"
          name="username"
          value={formData.username}
          onChange={handleChange}
          variant="outlined"
        />
      </div>




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