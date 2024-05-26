import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const NavBar = () => {
  const [showSubMenu, setShowSubMenu] = useState(false); // State to manage submenu visibility

  const navigate = useNavigate();
  const handleLogin = () => {
    navigate(`/`);
  };
  const handleHome = () => {
    navigate(`/home`);
  };
  const handleTablePerson = () => {
    navigate(`/table-person`);
  };
  const handleTableAddress = () => {
    navigate(`/table-address`);
  };
  const handleTableJedan = () => {
    navigate(`/table-jedan`);
  };
  const handleTableDva = () => {
    navigate(`/table-dva`);
  };
  const handleTableTri = () => {
    navigate(`/table-tri`);
  };
  const handleTableProduct = () => {
    navigate(`/table-product`);
  };
  const handleTableNovv = () => {
    navigate(`/table-novv`);
  };
  const handleTableSzagor = () => {
    navigate(`/table-szagor`);
  };
  const handleSubMenuClick = () => {
    setShowSubMenu(!showSubMenu);
  };

  return (
    <nav style={styles.nav}>
      <ul style={styles.navList}>
        <li style={styles.navItem}><a onClick={handleLogin} style={styles.navLink}>Login</a></li>
        <li style={styles.navItem}><a onClick={handleHome}  style={styles.navLink}>Home</a></li>
        <li style={styles.navItem}><a href="#" style={styles.navLink}>Logout</a></li>
        <li style={styles.navItem}>
          <a href="#" style={styles.navLink} onClick={handleSubMenuClick}>Elements</a>
          {showSubMenu && (
            <ul style={styles.subMenu}>
              <li><a onClick={handleTablePerson} style={styles.navLink} href="#">Table Person</a></li>
              <li><a onClick={handleTableAddress} style={styles.navLink} href="#">Table Address</a></li>
              <li><a onClick={handleTableJedan} style={styles.navLink} href="#">Table Jedan</a></li>
              <li><a onClick={handleTableDva} style={styles.navLink} href="#">Table Dva</a></li>
              <li><a onClick={handleTableTri} style={styles.navLink} href="#">Table Tri</a></li>
              <li><a onClick={handleTableProduct} style={styles.navLink} href="#">Table Product</a></li>
              <li><a onClick={handleTableNovv} style={styles.navLink} href="#">Table Novv</a></li>
              <li><a onClick={handleTableSzagor} style={styles.navLink} href="#">Table Szagor</a></li>
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
    textDecoration: 'none', // Remove underline
    color: '#fff', // Change font color to white

  },
  subMenu: {
    listStyleType: 'none',
    width: '200px',
    position: 'absolute',
    top: '200%', // Position submenu below the parent item
    left: 0,
    backgroundColor: '#3f51b5',
    padding: '0.5rem',
    borderRadius: '0.5rem',
    zIndex: 1, // Ensure submenu appears above other content
    textAlign: 'left',
  },
};

export default NavBar;