import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import ItemDelete from './ItemDelete';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import itemService from '../../services/ItemService';
import basketService from '../../services/BasketService';
import TextField from '@material-ui/core/TextField';

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

const ItemView = () => {
  const navigate = useNavigate();
  const classes = useStyles();

  const { id } = useParams();
  const [item, setItem] = useState(NaN)

  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await itemService.getOneItem(id);
          if (response.status === 200) {
              setItem(response.data);
          }
          const responsePerson = await itemService.getPersonsByItem(id);
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  }, [id]);

  const handleEdit = (id) => {
    navigate(`/edit-item/${id}`);
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
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const [formData, setFormData] = useState({
    quantity: '',
  });
  const handleAddToBasket = (e) => {
    const basketId= 1;
        const fetchData = async () => {
          try {
            console.log(formData)
            console.log(formData.quantity)
              const response = await basketService.addItemToBasket(basketId, id,formData.quantity);
              if (response.status === 200 || response.status === 201) {
                navigate(`/table-item`);
              }
          } catch (error) {
              console.error(error);
          }
      };
      fetchData();
  };

  return (
    <div className={classes.root}>
      <h2  >Nesto</h2>

      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <label>name: </label>
        <label> {  item.name  } </label>
      </div>
      <div className={classes.formGroup}>
        <label>quantity: </label>
        <label> {  item.quantity  } </label>
      </div><label>Input quantity: </label>
      <form className={classes.root}>
      <div className={classes.formGroup}>
      
        <TextField
          name="quantity"
          value={formData.quantity}
          type="number"
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      </form>
      <div className={classes.buttonGroup}>
        <Button variant="contained" color="primary" onClick={() => handleEdit(id)}>Edit</Button>
        <Button variant="contained" color="primary" onClick={() => handleAddToBasket(id)}>Add to basket</Button>
        <Button variant="contained" color="secondary" onClick={() => handleDelete(id)}>Delete</Button>
      </div>
      < ItemDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
    </div>
  );
};

export default ItemView;