import React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddressDelete = ({ open, id, onCancel, onDelete }) => {
  return (

    <Dialog open={open} onClose={onCancel}>
    <div>
    <ToastContainer />
      <DialogTitle>Confirmation of deleting</DialogTitle>
      <DialogContent>
        <p>
          Are you sure you want to delete this Address? {id}
        </p>
      </DialogContent>
      <DialogActions>
        <Button onClick={onCancel} color="primary">
          Cancel
        </Button>
        <Button onClick={() => onDelete(id)} color="secondary">
          Delete
        </Button>
      </DialogActions>
    </div>
    </Dialog>

  );
};

export default AddressDelete;