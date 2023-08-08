import { Outlet } from 'react-router-dom';

function App() {
  return (
    <>
      <div className=" title1">title-1</div>
      <div className="head-title3-regular">title-2</div>
      <Outlet />
    </>
  );
}

export default App;
