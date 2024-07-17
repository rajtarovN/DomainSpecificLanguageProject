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
import novvService from '../../services/NovvService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

import szagorService from '../../services/SzagorService';

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

const EditNovv = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    name: '',
  });
  const { id } = useParams();
  const [szagors, setSzagors] = useState([]);
  const [selectedSzagor, setSelectedSzagor] = useState('');


  useEffect(() => {
  const fetchDataSzagor = async () => {
      try {
          const response= await szagorService.getSzagor();
          if (response.status === 200) {
            setSzagors(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataSzagor();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await novvService.getOneNovv(id);
            if (response.status === 200) {
              setFormData({
                 name: response.data.name,

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
              for(let j in szagors){
                if (parseInt(selectedSzagor)===parseInt(szagors[j].id)){
                  formData['szagor'] = szagors[j];
                  break;
                }
              }
              const response = await novvService.updateNovv(formData, id);
              if (response.status === 200) {
                navigate(`/table-novv`);
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
              for(let j in szagors){
                if (parseInt(selectedSzagor)===parseInt(szagors[j].id)){ //todo ovde verujem da ide name
                  formData['szagor'] = szagors[j];
                  break;
                }
              }
          const response = await novvService.createNovv(formData);
          if (response.status === 200) {
              navigate(`/table-novv`);
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

    const handleChangeSzagor = (event) => {
      console.log(event.target.value)
        setSelectedSzagor(event.target.value);
    };



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




      <div>
            <label htmlFor="dvaSelect">Select Szagor: </label>
            <NativeSelect id="dvaSelect" value={selectedSzagor} onChange={handleChangeSzagor}>
                <option value="">--Please choose an option--</option>{
                 szagors.map(szagor => (
                    <option  key={
                    szagor.id} value={
                    szagor.id}>
                        {
                        szagor.id}
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

export default EditNovv;