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
import jedanService from '../../services/JedanService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

import triService from '../../services/TriService';
import dvaService from '../../services/DvaService';

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

const EditJedan = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    street: '',
  });
  const { id } = useParams();
  const [currentTri, setCurrentTri] = useState([]);
  const [allTri, setAllTri] = useState([]);
  const [dvas, setDvas] = useState([]);
  const [selectedDva, setSelectedDva] = useState('');


  useEffect(() => {
  const fetchDataDva = async () => {
      try {
          const response= await dvaService.getDva();
          if (response.status === 200) {
            setDvas(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataDva();

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await jedanService.getOneJedan(id);
            if (response.status === 200) {
              setFormData({
                 street: response.data.street,

              })
            }
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
      console.log('Component unmounted');
    };}
    const fetchDataTri = async () => {
      try {
          const response2 = await triService.getTri();
          if (response2.status === 200) {
            setAllTri(response2.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataTri();
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

              let liTri = [];
              for (let a of currentTri){
                liTri.push(a.id);
              }
              formData['triIds'] = liTri;
              for(let j in dvas){
                if (parseInt(selectedDva)===parseInt(dvas[j].id)){
                  formData['dva'] = dvas[j];
                  break;
                }
              }
              const response = await jedanService.updateJedan(formData, id);
              if (response.status === 200) {
                navigate(`/table-jedan`);
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
              let li1 = [];
              for (let a of currentTri){
                li1.push(a.id);
              }
              formData['triIds'] = li1;
              for(let j in dvas){
                if (parseInt(selectedDva)===parseInt(dvas[j].id)){ //todo ovde verujem da ide name
                  formData['dva'] = dvas[j];
                  break;
                }
              }
          const response = await jedanService.createJedan(formData);
          if (response.status === 200) {
              navigate(`/table-jedan`);
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

  const handleRemoveTri = (id) => {
    if (!currentTri || !allTri) return;

    for (const item of currentTri) {
      if (item.id === id) {
        const updatedCurrentTri = currentTri.filter(triItem => triItem.id !== id);
        setCurrentTri(updatedCurrentTri);
        const updatedAllTri = [...allTri, item];
        setAllTri(updatedAllTri);

        break;
      }
    }
  }


  const handleAddTri = (id) => {
    if (!currentTri || !allTri) return;

    for (const item of allTri) {
      if (item.id === id) {
        const updatedAllTri = allTri.filter(triItem => triItem.id !== id);
        setAllTri(updatedAllTri);
        const updatedCurrentTri = [...currentTri, item];
        setCurrentTri(updatedCurrentTri);

        break;
      }
    }
  }
    const handleChangeDva = (event) => {
      console.log(event.target.value)
        setSelectedDva(event.target.value);
    };



  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="street"
          name="street"
          value={formData.street}
          onChange={handleChange}
          variant="outlined"
        />
      </div>



        Tri
        <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Add</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {currentTri.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemoveTri(item.id)}>Remove</Button>
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
            {allTri.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleAddTri(item.id)}>Add</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <div>
            <label htmlFor="dvaSelect">Select Dva: </label>
            <NativeSelect id="dvaSelect" value={selectedDva} onChange={handleChangeDva}>
                <option value="">--Please choose an option--</option>{
                 dvas.map(dva => (
                    <option  key={
                    dva.id} value={
                    dva.id}>
                        {
                        dva.id}
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

export default EditJedan;