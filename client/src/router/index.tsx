import App from '@/App';
import Home from '@/pages/home';
import { Navigate, Outlet, createBrowserRouter } from 'react-router-dom';
import Guide from '@/pages/guide';
import Making from '@/pages/making';

const router = createBrowserRouter([
  {
    path: '',
    element: <App />,
    children: [
      {
        index: true,
        element: <Navigate to="model/LX06" />,
      },
      {
        path: 'model/:id',
        element: <Outlet />,
        children: [
          { index: true, element: <Home /> },
          {
            path: 'guide',
            element: <Outlet />,
            children: [
              {
                path: 'age',
                element: <Guide path="age" />,
              },
              {
                path: 'gender',
                element: <Guide path="gender" />,
              },
              {
                path: 'keyword',
                element: <Guide path="keyword" />,
              },
              {
                path: 'complete',
                element: <Guide path="complete" />,
              },
            ],
          },
          {
            path: 'making',
            element: <Outlet />,
            children: [
              {
                path: 'self/:step',
                element: <Making path="self" />,
              },
              { path: 'guide/:step', element: <Making path="self" /> },
            ],
          },
        ],
      },
    ],
  },
  {
    path: '*',
    element: <div>404</div>,
  },
]);

export default router;
