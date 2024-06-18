import { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import ItemDelete from './ItemDelete';
import { useNavigate } from 'react-router-dom';
import itemService from '../../services/ItemService';

const useStyles = makeStyles((theme) => ({
  card: {
    minWidth: 275,
    margin: theme.spacing(2),
  },
  button: {
    margin: theme.spacing(1),
  },
  title: {
    fontSize: 14,
  },
  pos: {
    marginBottom: 12,
  },
}));

const CardItem = () => {
  const classes = useStyles();
  const navigate = useNavigate();
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [delId, setDelId] = useState(-1);
  const [listItem, setListItem] = useState([]);

  const handleAdd = () => {
    navigate('/add-item');
  };

  const handleView = (id) => {
    navigate(`/item-view/${id}`);
  };

  const handleEdit = (id) => {
    navigate(`/edit-item/${id}`);
  };

  const handleDelete = (id) => {
    setDelId(id);
    setIsDialogOpen(true);
  };

  const handleCancelDelete = () => {
    setDelId(-1);
    setIsDialogOpen(false);
  };

  const handleConfirmDelete = async (id) => {
    try {
      const response = await itemService.deleteItem(id);
      if (response.status === 200) {
        setListItem(response.data);
      }
    } catch (error) {
      console.error(error);
    }
    setIsDialogOpen(false);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await itemService.getItem();
        if (response.status === 200) {
          setListItem(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      <br />
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <Button variant="contained" color="primary" onClick={handleAdd}>
          Add
        </Button>
        <Typography variant="h5" style={{ flexGrow: 1, textAlign: 'center' }}>
         Items
        </Typography>
      </div>
      <br />
      <Grid container spacing={3}>
        {listItem.map((item, index) => (
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
      <ItemDelete open={isDialogOpen} id={delId} onCancel={handleCancelDelete} onDelete={handleConfirmDelete} />
    </div>
  );
};

export default CardItem;
