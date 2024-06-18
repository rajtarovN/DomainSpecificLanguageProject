import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import BasketDelete from './BasketDelete';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import basketService from '../../services/BasketService';
import billService from '../../services/BillService';
import itemService from '../../services/ItemService';

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

const BasketView = () => {
  const navigate = useNavigate();
  const classes = useStyles();

  const { id } = useParams();
  const [basket, setBasket] = useState(NaN);
  const [allItem, setAllItem] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await basketService.getOneBasket(id);
          if (response.status === 200) {
            console.log(response.data)
              setBasket(response.data);
              setAllItem([response.data.item]);
          }
          const responsePerson = await basketService.getPersonsByBasket(id);
      } catch (error) {
          console.error(error);
      }
      
    // fetchDataItem();
  };
  fetchData();
  }, [id]);

  const handleEdit = (id) => {
    navigate(`/edit-basket/${id}`);
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
  const handleMakeBill = (e) => {
      e.preventDefault();
      const fetchData = async () => {
      const response = await billService.makeBillWithId(id);
      if (response.status === 200 || response.status === 201 ) {
        const idSaved = response.data.id
        navigate(`/bill-view/`+idSaved);
      }
       
    };
    fetchData();
  }
  const handleRemoveItem = (itemId) => {
    if ( !allItem) return;

    for (const item of allItem) {
      if (item.id === itemId) {
        const updatedCurrentItem = allItem.filter(itemItem => itemItem.id !== itemId);
        setAllItem(updatedCurrentItem);
        break;
      }
    }
    const fetchData = async () => {
    const response = await basketService.removeItem(id, itemId);
    }
    fetchData();
  }

  return (
    <div className={classes.root}>
      <h2  >Nesto</h2>

      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <label>formular: </label>
        <label> {  basket.formular  } </label>
      </div>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Name</TableCell>
              <TableCell align="center">Remove</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allItem.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  {item.name}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemoveItem(item.id)}>Remove</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer><br/>
      <div className={classes.buttonGroup}>
        <Button variant="contained" color="primary" onClick={handleMakeBill}>
         By
        </Button>
        <Button variant="contained" color="primary" onClick={() => handleEdit(id)}>Edit</Button>
        <Button variant="contained" color="secondary" onClick={() => handleDelete(id)}>Delete</Button>
      </div>
      < BasketDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
    </div>
  );
};

export default BasketView;