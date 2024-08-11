import React, { useState,  useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import ActionDelete from './ActionDelete';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import itemService from '../../services/ItemService';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
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

const ActionView = () => {
  const navigate = useNavigate();
  const classes = useStyles();
  const { id } = useParams();
  const [action, setAction] = useState(NaN)
  const [userType] = useState(
    JSON.parse(localStorage.getItem('user'))
        ? JSON.parse(localStorage.getItem('user')).userType
        : '');
    const [items, setItems] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await actionService.getOneAction(id);
          if (response.status === 200) {
              setAction(response.data);
          }
           const responseItems = await itemService.getItemsByActions(id);
          if (responseItems.status === 200) {
              setItems(responseItems.data);
          }
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  }, [id]);

  const handleEdit = (id) => {
    navigate(`/edit-action/${id}`);
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

 


        const handleView = (id) => {
        navigate(`/item-view/${id}`);
        };

  return (
  <div>
    <ToastContainer />
    <div className={classes.root}>
      <h2  >Nesto</h2>

      <p>ID: {id}</p>
      <div className={classes.formGroup}>
        <label>name: </label>
        <label> {  action.name  } </label>
      </div>
        <Grid container spacing={3}>
        {items.map((item, index) => (
          <Grid item key={index} xs={12} sm={6} md={4}>
            <Card className={classes.card}>
              <CardContent>
                <Typography className={classes.title} color="textSecondary" gutterBottom>
                  Index: {index + 1}
                </Typography>
                <Typography variant="h5" component="h2">
                  {item.name}
                </Typography>
                <Typography className={classes.pos} color="textSecondary">
                  Quantity: {item.quantity}
                </Typography>
              </CardContent>
              <CardActions>
                <Button size="small" variant="contained" color="secondary" onClick={() => handleView(item.id)}>
                  View
                </Button>

              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>


      <div className={classes.buttonGroup}>
        {(userType=='ADMIN' || userType=='SELLER') && (
         <Button variant="contained" color="primary" onClick={() => handleEdit(id)}>Edit</Button>)}
        {(userType=='ADMIN' || userType=='SELLER') && (
        <Button variant="contained" color="secondary" onClick={() => handleDelete(id)}>Delete</Button>)}

      </div>
      {(userType=='ADMIN' || userType=='SELLER') && (
      < ActionDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
      )}
    </div></div>
  );
};

export default ActionView;