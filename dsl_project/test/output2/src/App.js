import './App.css';
import Navbar from './views/components/NavBar';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import LoginForm from './views/login/LoginForm';
import ItemWithPriceEdit from "./views/ItemWithPrice/EditItemWithPrice";
import ItemWithPriceView from "./views/ItemWithPrice/ItemWithPriceView";
import TableItemWithPrice from "./views/ItemWithPrice/TableItemWithPrice";
import ActionEdit from "./views/Action/EditAction";
import ItemEdit from "./views/Item/EditItem";
import ItemView from "./views/Item/ItemView";
import TableItem from "./views/Item/TableItem";
import ActionView from "./views/Action/ActionView";
import TableAction from "./views/Action/TableAction";
import BillEdit from "./views/Bill/EditBill";
import BillView from "./views/Bill/BillView";
import TableBill from "./views/Bill/TableBill";
import BasketEdit from "./views/Basket/BasketEdit";
import BasketView from "./views/Basket/BasketView";
import TableBasket from "./views/Basket/TableBasket";
import PersonEdit from "./views/Person/EditPerson";
import PersonView from "./views/Person/PersonView";
import TablePerson from "./views/Person/TablePerson";
import CardItem from "./views/Item/CardItem";

//import Cards from './views/example/Cards';

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
          <Route path="/table-action" element={<TableAction/>}/>
          <Route path="/edit-action/:id" element={<ActionEdit/>}/>
          <Route path="/add-action" element={<ActionEdit/>}/>
          <Route path="/action-view/:id" element={<ActionView/>}/>
          <Route path="/table-item" element={<TableItem/>}/>
          <Route path="/edit-item/:id" element={<ItemEdit/>}/>
          <Route path="/add-item" element={<ItemEdit/>}/>
          <Route path="/item-view/:id" element={<ItemView/>}/>
          <Route path="/table-itemwithprice" element={<TableItemWithPrice/>}/>
          <Route path="/edit-itemwithprice/:id" element={<ItemWithPriceEdit/>}/>
          <Route path="/add-itemwithprice" element={<ItemWithPriceEdit/>}/>
          <Route path="/itemwithprice-view/:id" element={<ItemWithPriceView/>}/>
          <Route path="/table-bill" element={<TableBill/>}/>
          <Route path="/edit-bill/:id" element={<BillEdit/>}/>
          <Route path="/add-bill" element={<BillEdit/>}/>
          <Route path="/bill-view/:id" element={<BillView/>}/>
          <Route path="/table-basket" element={<TableBasket/>}/>
          <Route path="/edit-basket/:id" element={<BasketEdit/>}/>
          <Route path="/add-basket" element={<BasketEdit/>}/>
          <Route path="/basket-view/:id" element={<BasketView/>}/>
          <Route path="/table-person" element={<TablePerson/>}/>
          <Route path="/edit-person/:id" element={<PersonEdit/>}/>
          <Route path="/add-person" element={<PersonEdit/>}/>
          <Route path="/person-view/:id" element={<PersonView/>}/>
          <Route path="/card-item" element={<CardItem/>}/>
          {/*<Route path="/home" element={<Cards/>}/>*/}
        </Routes>
      </Router>
      {/* <CoffeeEdit></CoffeeEdit> */}
    </div>
  );
}

export default App;
