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
import itemWithPriceService from '../../services/ItemWithPriceService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

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

const EditItemWithPrice = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    currentPrice: 0,
  });
  const { id } = useParams();
  const [items, setItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState('');

  useEffect(() => {
    const fetchDataItem = async () => {
      try {
          const response= await itemService.getItem();
          if (response.status === 200) {
            setItems(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataItem();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await itemWithPriceService.getOneItemWithPrice(id);
            if (response.status === 200) {
              setFormData({

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
            for(let j in items){
              if (parseInt(selectedItem)===parseInt(items[j].id)){
                formData['item'] = items[j];
                
                break;
              }
            }
            setFormData({
              currentPrice: response.data.currentPrice,

           })
           console.log(formData);
              const response = await itemWithPriceService.updateItemWithPrice(formData, id);
              if (response.status === 200) {
                navigate(`/table-itemWithPrice`);
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
        for(let j in items){
          if (parseInt(selectedItem)===parseInt(items[j].id)){ //todo ovde verujem da ide name
            formData['item'] = items[j];
            break;
          }
        }
          const response = await itemWithPriceService.createItemWithPrice(formData);
          if (response.status === 200) {
              navigate(`/table-itemWithPrice`);
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

  const handleChangeItem = (event) => {
    console.log(event.target.value)
      setSelectedItem(event.target.value);
  };


  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <TextField
          label="currentPrice"
          name="currentPrice"
          value={formData.currentPrice}
          type="number"
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <label htmlFor="dvaSelect">Select Item: </label>
      <NativeSelect id="dvaSelect" value={selectedItem} onChange={handleChangeItem}>
                <option value="">--Please choose an option--</option>{
                 items.map(item => (
                    <option  key={
                    item.id} value={
                    item.id}>
                        {
                        item.id}
                    </option >
                ))}
            </NativeSelect>





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

export default EditItemWithPrice;