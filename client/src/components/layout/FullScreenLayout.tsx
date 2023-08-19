import { Outlet } from 'react-router-dom';

function FullScreenLayout() {
  return (
    <div className="h-full pt-80px">
      <Outlet />
    </div>
  );
}

export default FullScreenLayout;
