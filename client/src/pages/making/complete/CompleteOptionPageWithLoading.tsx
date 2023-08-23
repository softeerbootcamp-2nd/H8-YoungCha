import { useState, useEffect } from 'react';
import Loading from '../loading';
import { LOADING_DURATION } from '../loading/constant';
import CompleteOptionPage from './CompleteOptionPage';

function CompleteOptionPageWithLoading() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadingTime = setTimeout(() => {
      setIsLoading(false);
    }, LOADING_DURATION);
    return () => clearTimeout(loadingTime);
  }, []);

  return (
    <div className="flex-grow h-full flex flex-col">
      {isLoading ? <Loading /> : <CompleteOptionPage />}
    </div>
  );
}

export default CompleteOptionPageWithLoading;
