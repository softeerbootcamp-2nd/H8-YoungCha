import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import Home from './pages/home';

function App() {
  return (
    <>
      <Header />
      <Home />
      <Outlet />
    </>
  );
}

export default App;
