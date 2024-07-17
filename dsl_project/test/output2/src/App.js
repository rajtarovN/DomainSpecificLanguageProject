import './App.css';
import TablePerson from './views/Person/TablePerson';
import EditPerson from './views/Person/EditPerson';
import PersonView from './views/Person/PersonView';

import TableBill from './views/Bill/TableBill';
import EditBill from './views/Bill/EditBill';
import BillView from './views/Bill/BillView';

import TableItemWithPrice from './views/ItemWithPrice/TableItemWithPrice';
import EditItemWithPrice from './views/ItemWithPrice/EditItemWithPrice';
import ItemWithPriceView from './views/ItemWithPrice/ItemWithPriceView';

import TableBasket from './views/Basket/TableBasket';
import EditBasket from './views/Basket/EditBasket';
import BasketView from './views/Basket/BasketView';

import TableItem from './views/Item/TableItem';
import EditItem from './views/Item/EditItem';
import ItemView from './views/Item/ItemView';

import TableAction from './views/Action/TableAction';
import EditAction from './views/Action/EditAction';
import ActionView from './views/Action/ActionView';

import Navbar from './views/components/NavBar';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import LoginForm from './views/login/LoginForm';
import CardItem from './views/Item/CardItem';

const sampleData = [
  { id: 1, name: 'John', age: 30 },
  { id: 2, name: 'Jane', age: 25 },
  { id: 3, name: 'Doe', age: 40 },
];

function App() {
  return (
    <div className="App">

      <Router>
        <Navbar/>
        <Routes>
          <Route path="/" element={<LoginForm/>}/>
           <Route path="/home" element={<CardItem/>}/>
          nelly
          
          <Route path="/table-person" element={<TablePerson/>}/>
          <Route path="/edit-person/:id" element={<EditPerson/>}/>
          <Route path="/add-person" element={<EditPerson/>}/>
          <Route path="/person-view/:id" element={<PersonView/>}/>

          <Route path="/table-bill" element={<TableBill/>}/>
          <Route path="/edit-bill/:id" element={<EditBill/>}/>
          <Route path="/add-bill" element={<EditBill/>}/>
          <Route path="/bill-view/:id" element={<BillView/>}/>

          <Route path="/table-itemwithprice" element={<TableItemWithPrice/>}/>
          <Route path="/edit-itemwithprice/:id" element={<EditItemWithPrice/>}/>
          <Route path="/add-itemwithprice" element={<EditItemWithPrice/>}/>
          <Route path="/itemwithprice-view/:id" element={<ItemWithPriceView/>}/>

          <Route path="/table-basket" element={<TableBasket/>}/>
          <Route path="/edit-basket/:id" element={<EditBasket/>}/>
          <Route path="/add-basket" element={<EditBasket/>}/>
          <Route path="/basket-view/:id" element={<BasketView/>}/>

          <Route path="/table-item" element={<TableItem/>}/>
          <Route path="/edit-item/:id" element={<EditItem/>}/>
          <Route path="/add-item" element={<EditItem/>}/>
          <Route path="/item-view/:id" element={<ItemView/>}/>

          <Route path="/table-action" element={<TableAction/>}/>
          <Route path="/edit-action/:id" element={<EditAction/>}/>
          <Route path="/add-action" element={<EditAction/>}/>
          <Route path="/action-view/:id" element={<ActionView/>}/>

        </Routes>
      </Router>
      {/* <CoffeeEdit></CoffeeEdit> */}
    </div>
  );
}

export default App;
