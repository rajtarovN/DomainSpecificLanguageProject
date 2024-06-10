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
  },
  buttonGroup: {
    marginTop: theme.spacing(2),
  },
}));

const EditBasket = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    formular: '',
  });
  const { id } = useParams();
  const [persons, setPersons] = useState([]);
  const [selectedPerson, setSelectedPerson] = useState('');


  useEffect(() => {
  const fetchDataPerson = async () => {
      try {
          const response= await personService.getPerson();
          if (response.status === 200) {
            setPersons(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataPerson();

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
              for(let j in persons){
                if (parseInt(selectedPerson)===parseInt(persons[j].id)){
                  formData['person'] = persons[j];
                  break;
                }
              }
              const response = await basketService.updateBasket(formData, id);
              if (response.status === 200) {
                navigate(`/table-basket`);
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
              for(let j in persons){
                if (parseInt(selectedPerson)===parseInt(persons[j].id)){ //todo ovde verujem da ide name
                  formData['person'] = persons[j];
                  break;
                }
              }
          const response = await basketService.createBasket(formData);
          if (response.status === 200) {
              navigate(`/table-basket`);
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

    const handleChangePerson = (event) => {
      console.log(event.target.value)
        setSelectedPerson(event.target.value);
    };



  return (
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
            <label htmlFor="dvaSelect">Select Person: </label>
            <NativeSelect id="dvaSelect" value={selectedPerson} onChange={handleChangePerson}>
                <option value="">--Please choose an option--</option>{
                 persons.map(person => (
                    <option  key={
                    person.id} value={
                    person.id}>
                        {
                        person.id}
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
  );
};

export default EditBasket;