import App from '@/App';
import Home from '@/pages/home';
import { Navigate, createBrowserRouter } from 'react-router-dom';
import Guide from '@/pages/guide';
import FullScreenLayout from '@/components/layout/FullScreenLayout';
import MakingPageLayout from '@/pages/making/layout';
import MakingPage from '@/pages/making';

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
            path: 'making/:mode',
            element: <MakingPageLayout />,
            children: [
              {
                path: ':step',
                element: <MakingPage />,
              },
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
