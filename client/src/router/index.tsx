import App from '@/App';
import Home from '@/pages/home';
import FullScreenLayout from '@/components/layout/FullScreenLayout';
import { createBrowserRouter, Navigate } from 'react-router-dom';

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
        children: [
          { index: true, element: <Home /> },
          {
            path: 'guide',
            element: <FullScreenLayout />,
            children: [
              {
                path: 'age',
                element: <div>age</div>,
              },
              {
                path: 'gender',
                element: <div>gender</div>,
              },
              {
                path: 'keyword',
                element: <div>keyword</div>,
              },
              {
                path: 'compelete',
                element: <div>compelete</div>,
              },
            ],
          },
          {
            path: 'making',
            element: <FullScreenLayout />,
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
