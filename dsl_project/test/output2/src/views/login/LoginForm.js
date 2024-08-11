import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AuthService from '../../services/auth.service';
import { useNavigate } from 'react-router-dom';
import logoImage from '../../assets/logo.png';

const useStyles = makeStyles((theme) => ({
  container: {
    display: 'flex',
    width: '100vw',
    height: '100vh',
    padding: '0 20px',
  },
  formContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start', // Moves the form higher
    alignItems: 'flex-start',
    width: '70%', // Slightly reduced width for more rightward positioning
    padding: '60px 50px 0 0', // Adds top padding to move the form down and padding-right
    boxSizing: 'border-box',
    margin: '0 auto 0 100px', // Center horizontally with more space at the top
  },
  imageContainer: {
    width: '100%', // Adjusts width for more space from the right edge
    height: '60%', // Adjusts height to fit better
    backgroundImage: `url(${logoImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-start',
    margin: 'auto', // Centers the image container vertically and horizontally
    marginRight: '200px', // Moves the image away from the right edge
    marginTop: '30px',
    borderRadius: '20px', // Adds rounded corners with a 20px radius
  },
  formGroup: {
    marginBottom: theme.spacing(2),
  },
  buttonGroup: {
    marginTop: theme.spacing(2),
  },
}));

const LoginForm = () => {
  let navigate = useNavigate();
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
    AuthService.login(formData).then(
      () => {
        toast.success('You are logged in.');
        navigate('/home');
      }, (error) =>{
        toast.error('Failed to login.');
      }
    )
    console.log(formData);
  };

  return (
    <div className={classes.container}>
      <ToastContainer />
      <div className={classes.formContainer}>
        <form onSubmit={handleSubmit}>
          <h2>Log in</h2>
          <div className={classes.formGroup}>
            <TextField
              style={
              { width: '300px' }
              }
              label="Username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              variant="outlined"
            />
          </div><br/>
          <div className={classes.formGroup}>
            <TextField
              style={
              { width: '300px' }
              }
              label="Password"
              name="password"
              type='password'
              value={formData.password}
              onChange={handleChange}
              variant="outlined"
            />
          </div><br/>
          <div className={classes.buttonGroup}>
            <Button variant="contained" color="primary" type="submit">
              Log in
            </Button>
          </div>
        </form>
      </div>
      <div style={
      { display: 'flex', alignItems: 'center', justifyContent: 'flex-start', width: '55%' }
      }>
        <div className={classes.imageContainer}></div>
      </div>
    </div>
  );
};

export default LoginForm;