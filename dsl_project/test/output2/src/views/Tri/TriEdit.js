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
import triService from '../../services/TriService';
import { useNavigate } from 'react-router-dom';
import NativeSelect from '@mui/material/NativeSelect';

import jedanService from '../../services/JedanService';
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

const EditTri = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    sdssdfe: '',
  });
  const { id } = useParams();
  const [jedans, setJedans] = useState([]);
  const [selectedJedan, setSelectedJedan] = useState('');

  const [currentDva, setCurrentDva] = useState([]);
  const [allDva, setAllDva] = useState([]);

  useEffect(() => {
  const fetchDataJedan = async () => {
      try {
          const response= await jedanService.getJedan();
          if (response.status === 200) {
            setJedans(response.data);
        }
      } catch (error) {
          console.error(error);
      }
  };
  fetchDataJedan();
const fetchDataDva = async () => {
      try {
          console.log("novovoo")
          const response2 = await dvaService.getDva();
          console.log(response2)
          if (response2.status === 200) {
            setAllDva(response2.data);
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
            const response = await triService.getOneTri(id);
            if (response.status === 200) {
              setFormData({
                 sdssdfe: response.data.sdssdfe,

              })
            }
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
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
    console.log(id)
    if (id!=null){
        e.preventDefault();
        const fetchData = async () => {
          try {
              for(let j in jedans){
                if (parseInt(selectedJedan)===parseInt(jedans[j].id)){
                  formData['jedan'] = jedans[j];
                  break;
                }
              }

              let liDva = [];
              for (let a of currentDva){
                liDva.push(a.id);
              }
              formData['dvaIds'] = liDva;
              const response = await triService.updateTri(formData, id);
              if (response.status === 200) {
                navigate(`/table-tri`);
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
              for(let j in jedans){
                if (parseInt(selectedJedan)===parseInt(jedans[j].id)){ //todo ovde verujem da ide name
                  formData['jedan'] = jedans[j];
                  break;
                }
              }
              let li1 = [];
              for (let a of currentDva){
                li1.push(a.id);
              }
              formData['dvaIds'] = li1;
          const response = await triService.createTri(formData);
          if (response.status === 200) {
              navigate(`/table-tri`);
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

    const handleChangeJedan = (event) => {
      console.log(event.target.value)
        setSelectedJedan(event.target.value);
    };
  const handleRemoveDva = (id) => {
    if (!currentDva || !allDva) return;

    for (const item of currentDva) {
      if (item.id === id) {
        const updatedCurrentDva = currentDva.filter(dvaItem => dvaItem.id !== id);
        setCurrentDva(updatedCurrentDva);
        const updatedAllDva = [...allDva, item];
        setAllDva(updatedAllDva);

        break;
      }
    }
  }


  const handleAddDva = (id) => {
    if (!currentDva || !allDva) return;

    for (const item of allDva) {
      if (item.id === id) {
        const updatedAllDva = allDva.filter(dvaItem => dvaItem.id !== id);
        setAllDva(updatedAllDva);
        const updatedCurrentDva = [...currentDva, item];
        setCurrentDva(updatedCurrentDva);

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
          label="sdssdfe"
          name="sdssdfe"
          value={formData.sdssdfe}
          onChange={handleChange}
          variant="outlined"
        />
      </div>




      <div>
            <label htmlFor="dvaSelect">Select Jedan: </label>
            <NativeSelect id="dvaSelect" value={selectedJedan} onChange={handleChangeJedan}>
                <option value="">--Please choose an option--</option>{
                 jedans.map(jedan => (
                    <option  key={
                    jedan.id} value={
                    jedan.id}>
                        {
                        jedan.id}
                    </option >
                ))}
            </NativeSelect>
        </div>

        Dva
        <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Id</TableCell>
              <TableCell align="center">Add</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {currentDva.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleRemoveDva(item.id)}>Remove</Button>
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
            {allDva.map((item, index) => (
              <TableRow key={index}>
                <TableCell align="center">
                  {item.id}
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleAddDva(item.id)}>Add</Button>
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

export default EditTri;