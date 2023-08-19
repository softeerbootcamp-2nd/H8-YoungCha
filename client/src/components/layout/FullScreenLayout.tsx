import { Outlet } from 'react-router-dom';

function FullScreenLayout() {
  return (
    <div className="h-full pt-80px min-w-768px">
      <Outlet />
    </div>
  );
}

export default FullScreenLayout;
