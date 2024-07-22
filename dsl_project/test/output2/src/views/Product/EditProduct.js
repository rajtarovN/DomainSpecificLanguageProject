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
import productService from '../../services/ProductService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import personService from '../../services/PersonService';

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

const EditProduct = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    name: '',
    price: '',
  });
  const { id } = useParams();
  const [currentPerson, setCurrentPerson] = useState([]);
  const [allPerson, setAllPerson] = useState([]);

  useEffect(() => {



    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await productService.getOneProduct(id);
            if (response.status === 200) {
              setFormData({
                 name: response.data.name,
                 price: response.data.price,
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
    const fetchDataPerson = async () => {
      try {
          const response2 = await personService.getPerson();
          if (response2.status === 200) {
            setAllPerson(response2.data);
        }
      } catch (error) {
       toast.error('Failed to create item. Please try again.');
          console.error(error);
      }
  };
  fetchDataPerson();
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

              let liPerson = [];
              for (let a of currentPerson){
                liPerson.push(a.id);
              }
              formData['personIds'] = liPerson;
              const response = await productService.updateProduct(formData, id);
              if (response.status === 200) {
               toast.success('Item created successfully!');
                navigate(`/table-product`);
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
              for (let a of currentPerson){
                li1.push(a.id);
              }
              formData['personIds'] = li1;
          const response = await productService.createProduct(formData);
          if (response.status === 200) {
           toast.success('Item created successfully!');
              navigate(`/table-product`);
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

  const handleRemovePerson = (id) => {
    if (!currentPerson || !allPerson) return;

    for (const item of currentPerson) {
      if (item.id === id) {
        const updatedCurrentPerson = currentPerson.filter(personItem => personItem.id !== id);
        setCurrentPerson(updatedCurrentPerson);
        const updatedAllPerson = [...allPerson, item];
        setAllPerson(updatedAllPerson);

        break;
      }
    }
  }


  const handleAddPerson = (id) => {
    if (!currentPerson || !allPerson) return;

    for (const item of allPerson) {
      if (item.id === id) {
        const updatedAllPerson = allPerson.filter(personItem => personItem.id !== id);
        setAllPerson(updatedAllPerson);
        const updatedCurrentPerson = [...currentPerson, item];
        setCurrentPerson(updatedCurrentPerson);

        break;
      }
    }
  }


  return (
  <div>
    <ToastContainer />
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
          label="price"
          name="price"
          value={formData.price}
          type="number"
          onChange={handleChange}
          variant="outlined"
        />
      </div>



        Person
        <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Add</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {currentPerson.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemovePerson(item.id)}>Remove</Button>
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
            {allPerson.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleAddPerson(item.id)}>Add</Button>
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
    </div>
  );
};

export default EditProduct;