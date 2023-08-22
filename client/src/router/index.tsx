import App from '@/App';
import Home from '@/pages/home';
import { Navigate, createBrowserRouter } from 'react-router-dom';
import Guide from '@/pages/guide';
import Making from '@/pages/making';
import FullScreenLayout from '@/components/layout/FullScreenLayout';
import ErrorPage from '@/pages/error';

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
            element: <FullScreenLayout />,
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
    errorElement: <ErrorPage />,
  },
  {
    path: '*',
    element: <ErrorPage errorType="404" />,
  },
]);

export default router;
