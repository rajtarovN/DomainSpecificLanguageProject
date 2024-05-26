import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useParams } from 'react-router-dom';

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

const LoginForm = () => {
  const classes = useStyles();
  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });
  const { id } = useParams();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);
  };

  const handleCancel = () => {
    console.log('Form canceled');
  };

  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      <h2  >Log in</h2>
      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <TextField
          label="Username"
          name="username"
          value={formData.username}
          onChange={handleChange}
          variant="outlined"
        />
      </div>
      <div className={classes.formGroup}>
        <TextField
          label="Password"
          name="password"
          type='password'
          value={formData.password}
          onChange={handleChange}
          variant="outlined"
        />
      </div>

      <div className={classes.buttonGroup}>
        <Button variant="contained" color="primary" type="submit">
          Log in
        </Button>
      </div>
    </form>
  );
};

export default LoginForm;