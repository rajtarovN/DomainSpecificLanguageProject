import './App.css';
import TablePerson from './views/Person/TablePerson';
import PersonEdit from './views/Person/PersonEdit';
import PersonView from './views/Person/PersonView';

import TableAddress from './views/Address/TableAddress';
import AddressEdit from './views/Address/AddressEdit';
import AddressView from './views/Address/AddressView';

import TableJedan from './views/Jedan/TableJedan';
import JedanEdit from './views/Jedan/JedanEdit';
import JedanView from './views/Jedan/JedanView';

import TableDva from './views/Dva/TableDva';
import DvaEdit from './views/Dva/DvaEdit';
import DvaView from './views/Dva/DvaView';

import TableTri from './views/Tri/TableTri';
import TriEdit from './views/Tri/TriEdit';
import TriView from './views/Tri/TriView';

import TableProduct from './views/Product/TableProduct';
import ProductEdit from './views/Product/ProductEdit';
import ProductView from './views/Product/ProductView';

import TableNovv from './views/Novv/TableNovv';
import NovvEdit from './views/Novv/NovvEdit';
import NovvView from './views/Novv/NovvView';

import TableSzagor from './views/Szagor/TableSzagor';
import SzagorEdit from './views/Szagor/SzagorEdit';
import SzagorView from './views/Szagor/SzagorView';

import Navbar from './views/components/NavBar';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import LoginForm from './views/login/LoginForm';
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
          {/*<Route path="/home" element={<Cards/>}/>*/}
          <Route path="/table-person" element={<TablePerson/>}/>
          <Route path="/edit-person/:id" element={<PersonEdit/>}/>
          <Route path="/add-person" element={<PersonEdit/>}/>
          <Route path="/person-view/:id" element={<PersonView/>}/>

          <Route path="/table-address" element={<TableAddress/>}/>
          <Route path="/edit-address/:id" element={<AddressEdit/>}/>
          <Route path="/add-address" element={<AddressEdit/>}/>
          <Route path="/address-view/:id" element={<AddressView/>}/>

          <Route path="/table-jedan" element={<TableJedan/>}/>
          <Route path="/edit-jedan/:id" element={<JedanEdit/>}/>
          <Route path="/add-jedan" element={<JedanEdit/>}/>
          <Route path="/jedan-view/:id" element={<JedanView/>}/>

          <Route path="/table-dva" element={<TableDva/>}/>
          <Route path="/edit-dva/:id" element={<DvaEdit/>}/>
          <Route path="/add-dva" element={<DvaEdit/>}/>
          <Route path="/dva-view/:id" element={<DvaView/>}/>

          <Route path="/table-tri" element={<TableTri/>}/>
          <Route path="/edit-tri/:id" element={<TriEdit/>}/>
          <Route path="/add-tri" element={<TriEdit/>}/>
          <Route path="/tri-view/:id" element={<TriView/>}/>

          <Route path="/table-product" element={<TableProduct/>}/>
          <Route path="/edit-product/:id" element={<ProductEdit/>}/>
          <Route path="/add-product" element={<ProductEdit/>}/>
          <Route path="/product-view/:id" element={<ProductView/>}/>

          <Route path="/table-novv" element={<TableNovv/>}/>
          <Route path="/edit-novv/:id" element={<NovvEdit/>}/>
          <Route path="/add-novv" element={<NovvEdit/>}/>
          <Route path="/novv-view/:id" element={<NovvView/>}/>

          <Route path="/table-szagor" element={<TableSzagor/>}/>
          <Route path="/edit-szagor/:id" element={<SzagorEdit/>}/>
          <Route path="/add-szagor" element={<SzagorEdit/>}/>
          <Route path="/szagor-view/:id" element={<SzagorView/>}/>

        </Routes>
      </Router>
      {/* <CoffeeEdit></CoffeeEdit> */}
    </div>
  );
}

export default App;
