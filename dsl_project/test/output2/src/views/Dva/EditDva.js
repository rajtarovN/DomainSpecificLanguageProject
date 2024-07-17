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
import dvaService from '../../services/DvaService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

import jedanService from '../../services/JedanService';
import triService from '../../services/TriService';

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

const EditDva = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    sds: '',
  });
  const { id } = useParams();
  const [currentJedan, setCurrentJedan] = useState([]);
  const [allJedan, setAllJedan] = useState([]);
  const [currentTri, setCurrentTri] = useState([]);
  const [allTri, setAllTri] = useState([]);

  useEffect(() => {

    if (id!=null){
    return () => {
      const fetchData = async () => {
        try {
            const response = await dvaService.getOneDva(id);
            if (response.status === 200) {
              setFormData({
                 sds: response.data.sds,

              })
            }
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
      console.log('Component unmounted');
    };}
    const fetchDataJedan = async () => {
      try {
          const response2 = await jedanService.getJedan();
          if (response2.status === 200) {
            setAllJedan(response2.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataJedan();
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

              let liJedan = [];
              for (let a of currentJedan){
                liJedan.push(a.id);
              }
              formData['jedanIds'] = liJedan;

              const response = await dvaService.updateDva(formData, id);
              if (response.status === 200) {
                navigate(`/table-dva`);
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
              for (let a of currentJedan){
                li1.push(a.id);
              }
              formData['jedanIds'] = li1;
          const response = await dvaService.createDva(formData);
          if (response.status === 200) {
              navigate(`/table-dva`);
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

  const handleRemoveJedan = (id) => {
    if (!currentJedan || !allJedan) return;

    for (const item of currentJedan) {
      if (item.id === id) {
        const updatedCurrentJedan = currentJedan.filter(jedanItem => jedanItem.id !== id);
        setCurrentJedan(updatedCurrentJedan);
        const updatedAllJedan = [...allJedan, item];
        setAllJedan(updatedAllJedan);

        break;
      }
    }
  }


  const handleAddJedan = (id) => {
    if (!currentJedan || !allJedan) return;

    for (const item of allJedan) {
      if (item.id === id) {
        const updatedAllJedan = allJedan.filter(jedanItem => jedanItem.id !== id);
        setAllJedan(updatedAllJedan);
        const updatedCurrentJedan = [...currentJedan, item];
        setCurrentJedan(updatedCurrentJedan);

        break;
      }
    }
  }
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



  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="sds"
          name="sds"
          value={formData.sds}
          onChange={handleChange}
          variant="outlined"
        />
      </div>



        Jedan
        <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Add</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {currentJedan.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemoveJedan(item.id)}>Remove</Button>
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
            {allJedan.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleAddJedan(item.id)}>Add</Button>
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

export default EditDva;