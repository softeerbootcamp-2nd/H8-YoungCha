import App from '@/App';
import Home from '@/pages/home';
import PrepareGuide from '@/pages/prepare-guide';
import { Navigate, Outlet, createBrowserRouter } from 'react-router-dom';

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
                element: <PrepareGuide path="age" />,
              },
              {
                path: 'gender',
                element: <PrepareGuide path="gender" />,
              },
              {
                path: 'keyword',
                element: <PrepareGuide path="keyword" />,
              },
              {
                path: 'compelete',
                element: <div>compelete</div>,
              },
            ],
          },
          {
            path: 'making',
            element: <Outlet />,
            children: [
              {
                path: 'self/:step',
                element: <div>self</div>,
              },
              { path: 'guide/:step', element: <div>guide</div> },
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
