import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import BillDelete from './BillDelete';
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

const BillView = () => {
  const navigate = useNavigate();
  const classes = useStyles();
  const { id } = useParams();
  const [bill, setBill] = useState(NaN)

  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await billService.getOneBill(id);
          if (response.status === 200) {
              setBill(response.data);
          }
          const responseCustomer = await billService.getCustomersByBill(id);
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  }, [id]);

  const handleEdit = (id) => {
    navigate(`/edit-bill/${id}`);
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

 //todo


  return (
  <div>
    <ToastContainer />
    <div className={classes.root}>
      <h2  >Nesto</h2>

      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <label>neki_tekst: </label>
        <label> {  bill.neki_tekst  } </label>
      </div>

      <div className={classes.buttonGroup}>
        <Button variant="contained" color="primary" onClick={() => handleEdit(id)}>Edit</Button>
        <Button variant="contained" color="secondary" onClick={() => handleDelete(id)}>Delete</Button>

      </div>
      < BillDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
    </div></div>
  );
};

export default BillView;