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
import BillDelete from './BillDelete';
import { useNavigate } from 'react-router-dom';
import billService from '../../services/BillService';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

const TableBill = () => {

  const handleAdd = () => {
    navigate(`/add-bill`);
  };
  const handleView = (id) => {

    navigate(`/bill-view/`+id);
  };

  const navigate = useNavigate();
  const handleEdit = (id) => {
    navigate(`/edit-bill/${id}`);
  };
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [delId, setDelId] = useState(-1);
  const [listBill, setListBill] = useState([]);

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
          const response = await billService.deleteBill(id);
          if (response.status === 200) {
              setListBill(response.data);
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
            const response = await billService.getBill();
            if (response.status === 200) {
                setListBill(response.data);
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
      <br/>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Index</TableCell>
              <TableCell align="center">cashier</TableCell>
              <TableCell align="center">view</TableCell>
              <TableCell align="center">edit</TableCell>
              <TableCell align="center">delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {listBill.map((item, index) => (
              <TableRow key={index}>
               <TableCell align="center">{index + 1}</TableCell>
                <TableCell align="center">{item.cashier}</TableCell>
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
      < BillDelete
        open={isDialogOpen}
        id={delId}
        onCancel={handleCancelDelete}
        onDelete={handleConfirmDelete}
      />
    </div>
    </div>
  );
};

export default TableBill;