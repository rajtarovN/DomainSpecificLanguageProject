import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const NavBar = () => {
  const [showSubMenu, setShowSubMenu] = useState(false); // State to manage submenu visibility
  const [userType] = useState(
    JSON.parse(localStorage.getItem('user'))
        ? JSON.parse(localStorage.getItem('user')).userType
        : ''
  );
  const handleRegister = () => {
    navigate(`/register`);
  };

  const navigate = useNavigate();
  const handleLogin = () => {
    navigate(`/`);
  };
  const handleHome = () => {
    navigate(`/home`);
  };
  const handleTableBill = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-bill`);
  };
  const handleTableItemWithPrice = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-itemwithprice`);
  };
   const handleBasketView = () => {
    setShowSubMenu(!showSubMenu);
    const id= JSON.parse(localStorage.getItem('user'))
    ? JSON.parse(localStorage.getItem('id')).userType : "";
    navigate(`/edit-basket/`+id);
  };
  const handleTableItem = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-item`);
  };
  const handleTableAction = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-action`);
  };
  const handleTableAddress = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-address`);
  };
  const handleTableSeller = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-seller`);
  };
  const handleTableAdmin = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-admin`);
  };
  const handleTableCustomer = () => {
  setShowSubMenu(!showSubMenu);
    navigate(`/table-customer`);
  };
  const handleSubMenuClick = () => {
    setShowSubMenu(!showSubMenu);
  };
  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate(`/`);
  };

  return (
    <nav style={styles.nav}>
      <ul style={styles.navList}>
         {!userType && (
          <>
            <li style={styles.navItem}>
              <a onClick={handleRegister} style={styles.navLink}>Register</a>
            </li>
            <li style={styles.navItem}>
              <a onClick={handleLogin} style={styles.navLink}>Login</a>
            </li>
          </>
        )}
        {userType && (
          <li style={styles.navItem}>
            <a onClick={handleLogout} style={styles.navLink}>Logout</a>
          </li>
        )}
        <li style={styles.navItem}><a onClick={handleHome}  style={styles.navLink}>Home</a></li>
        <li style={styles.navItem}><a href="#" style={styles.navLink}>Logout</a></li>
        <li style={styles.navItem}>
          <a href="#" style={styles.navLink} onClick={handleSubMenuClick}>Elements</a>
          {showSubMenu && (
            <ul style={styles.subMenu}>
                <li><a onClick={handleTableBill} style={styles.navLink} href="#">Table Bill</a></li>
                <li><a onClick={handleTableItemWithPrice} style={styles.navLink} href="#">Table ItemWithPrice</a></li>
                {/* <li><a onClick={handleTableBasket} style={styles.navLink} href="#">Table Basket</a></li> */}
                <li><a onClick={handleTableItem} style={styles.navLink} href="#">Table Item</a></li>
                <li><a onClick={handleTableAction} style={styles.navLink} href="#">Table Action</a></li>
                <li><a onClick={handleTableAddress} style={styles.navLink} href="#">Table Address</a></li>
                 {(userType=='ADMIN' || userType=='SELLER') && (
                <li><a onClick={handleTableSeller} style={styles.navLink} href="#">Table Seller</a></li>)}
                {(userType=='ADMIN') && (
                 <li><a onClick={handleTableAdmin} style={styles.navLink} href="#">Table Admin</a></li>)}
                 {(userType=='ADMIN' || userType=='SELLER') && (
                <li><a onClick={handleTableCustomer} style={styles.navLink} href="#">Table Customer</a></li>)}
            </ul>
          )}
        </li>

      </ul>
    </nav>
  );
};

const styles = {
  nav: {
    backgroundColor: '#3f51b5',
    padding: '1rem',
  },
  navList: {
    listStyleType: 'none',
    margin: 0,
    padding: 0,
    display: 'flex',
    justifyContent: 'center',
  },
  navItem: {
    margin: '0 1rem',
    position: 'relative',
  },
  navLink: {
    textDecoration: 'none',
    color: '#fff',

  },
  subMenu: {
    listStyleType: 'none',
    width: '200px',
    position: 'absolute',
    top: '200%',
    left: 0,
    backgroundColor: '#3f51b5',
    padding: '0.5rem',
    borderRadius: '0.5rem',
    zIndex: 1,
    textAlign: 'left',
  },
};

export default NavBar;