import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import AddressDelete from './AddressDelete';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import addressService from '../../services/AddressService';
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

const AddressView = () => {
  const navigate = useNavigate();
  const classes = useStyles();
  const { id } = useParams();
  const [address, setAddress] = useState(NaN)
  const [userType] = useState(
    JSON.parse(localStorage.getItem('user'))
        ? JSON.parse(localStorage.getItem('user')).userType
        : '');
  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await addressService.getOneAddress(id);
          if (response.status === 200) {
              setAddress(response.data);
          }
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  }, [id]);

  const handleEdit = (id) => {
    navigate(`/edit-address/${id}`);
  };
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [delId, setDelId] = useState(-1);

  const handleDelete = (id) => {
    setDelId(id)
    setIsDialogOpen(true);
  };
  const handleCancelDelete = () => {
    setDelId(-1)
    setIsDialogOpen(false);
  };

  const handleConfirmDelete = (coffeeId) => {
    console.log("Deleting coffee with ID:", coffeeId);
    setIsDialogOpen(false);
  };




  return (
  <div>
    <ToastContainer />
    <div className={classes.root}>
      <h2  >Nesto</h2>

      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <label>street: </label>
        <label> {  address.street  } </label>
      </div>
      <div className={classes.formGroup}>
        <label>number: </label>
        <label> {  address.number  } </label>
      </div>
      <div className={classes.formGroup}>
        <label>zip: </label>
        <label> {  address.zip  } </label>
      </div>

      <div className={classes.buttonGroup}>
        {(userType=='ADMIN' || userType=='SELLER') && (
         <Button variant="contained" color="primary" onClick={() => handleEdit(id)}>Edit</Button>)}
        {(userType=='ADMIN' || userType=='SELLER') && (
        <Button variant="contained" color="secondary" onClick={() => handleDelete(id)}>Delete</Button>)}

      </div>
      {(userType=='ADMIN' || userType=='SELLER') && (
      < AddressDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
      )}
    </div></div>
  );
};

export default AddressView;