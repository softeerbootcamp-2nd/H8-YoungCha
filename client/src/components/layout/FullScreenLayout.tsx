import { Outlet } from 'react-router-dom';

function FullScreenLayout() {
  return (
    <div className="h-full mt-85px">
      <Outlet />
    </div>
  );
}

export default FullScreenLayout;
