import './App.css';
import TablePerson from './views/Person/TablePerson';
import EditPerson from './views/Person/EditPerson';
import PersonView from './views/Person/PersonView';

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

        </Routes>
      </Router>
      {/* <CoffeeEdit></CoffeeEdit> */}
    </div>
  );
}

export default App;
