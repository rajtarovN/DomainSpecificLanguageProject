import {useState, React, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import AddressDelete from './AddressDelete';
import { useNavigate } from 'react-router-dom';
import addressService from '../../services/AddressService';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

const TableAddress = () => {

  const handleAdd = () => {
    navigate(`/add-address`);
  };
  const handleView = (id) => {

    navigate(`/address-view/`+id);
  };

  const navigate = useNavigate();
  const handleEdit = (id) => {
    navigate(`/edit-address/${id}`);
  };
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [delId, setDelId] = useState(-1);
  const [listAddress, setListAddress] = useState([]);

  const handleDelete = (id) => {
    setDelId(id)
    setIsDialogOpen(true);
  };

  const handleCancelDelete = () => {
    setDelId(-1)
    setIsDialogOpen(false);
  };

  const handleConfirmDelete = (id) => {
    const fetchData = async () => {
      try {
          const response = await addressService.deleteAddress(id);
          if (response.status === 200) {
              setListAddress(response.data);
              console.log(response.data);
          }
      } catch (error) {
          console.error(error);
      }
  };
  fetchData();
  setIsDialogOpen(false);
  }

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await addressService.getAddress();
            if (response.status === 200) {
                setListAddress(response.data);
            }
        } catch (error) {
            console.error(error);
        }
    };

    fetchData();
}, []);

  const classes = useStyles();

  return (
  <div>
    <ToastContainer />
    <div>
      <br/>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
          <Button variant="contained" color="primary" onClick={ handleAdd }>Add</Button>
          <h3 style={{ flexGrow: 1, textAlign: 'center' }}>Address</h3>
      </div>
      <br/>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Index</TableCell>
              <TableCell align="center">street</TableCell>
              <TableCell align="center">city</TableCell>
              <TableCell align="center">zipCode</TableCell>
              <TableCell align="center">view</TableCell>
              <TableCell align="center">edit</TableCell>
              <TableCell align="center">delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {listAddress.map((item, index) => (
              <TableRow key={index}>
               <TableCell align="center">{index + 1}</TableCell>
                <TableCell align="center">{item.street}</TableCell>
                <TableCell align="center">{item.city}</TableCell>
                <TableCell align="center">{item.zipCode}</TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleView(item.id)}>View</Button>
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="primary" onClick={() => handleEdit(item.id)}>Edit</Button>
                </TableCell>
                <TableCell align="center">
                  <Button variant="contained" color="secondary" onClick={() => handleDelete(item.id)}>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      < AddressDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
    </div>
    </div>
  );
};

export default TableAddress;