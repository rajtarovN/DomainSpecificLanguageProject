import './App.css';
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
        </Routes>
      </Router>
      {/* <CoffeeEdit></CoffeeEdit> */}
    </div>
  );
}

export default App;
