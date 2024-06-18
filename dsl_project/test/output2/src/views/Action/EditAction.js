import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import actionService from '../../services/ActionService';

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

const EditAction = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    name: '',
    dateFrom: '',
    dateTo: '',
    originalCode: '',
  });
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      const fetchData = async () => {
        try {
          const response = await actionService.getOneAction(id);
          if (response.status === 200) {
            setFormData({
              name: response.data.name,
              dateFrom: response.data.dateFrom,
              dateTo: response.data.dateTo,
              originalCode: response.data.originalCode
            });
          }
        } catch (error) {
          console.error(error);
        }
      };
      fetchData();
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const fetchData = async () => {
      try {
        let response;
        if (id) {
          response = await actionService.updateAction(formData, id);
        } else {
          response = await actionService.createAction(formData);
        }
        if (response.status === 200) {
          navigate(`/table-action`);
        }
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  };

  const handleCancel = () => {
    console.log('Form canceled');
  };

  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2>My Form</h2>
      <p>ID: {id}</p>

      <div className={classes.formGroup}>
        <TextField
          label="Name"
          name="name"
          value={formData.name}
          onChange={handleChange}
          variant="outlined"
        />
      </div>

      <div className={classes.formGroup}>
        <TextField
          label="Original Code"
          name="originalCode"
          value={formData.originalCode}
          onChange={handleChange}
          variant="outlined"
        />
      </div>

      <div className={classes.formGroup}>
        <TextField
          label="Date From"
          name="dateFrom"
          type="date"
          value={formData.dateFrom}
          onChange={handleChange}
          variant="outlined"
          InputLabelProps={{
            shrink: true,
          }}
        />
      </div>

      <div className={classes.formGroup}>
        <TextField
          label="Date To"
          name="dateTo"
          type="date"
          value={formData.dateTo}
          onChange={handleChange}
          variant="outlined"
          InputLabelProps={{
            shrink: true,
          }}
        />
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

export default EditAction;
