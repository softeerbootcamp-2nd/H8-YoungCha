import { useState, useEffect } from 'react';
import { LOADING_DURATION } from '../making/loading/constant';
import Loading from '../making/loading';
import CompleteGuide from './CompleteGuide';

function CompleteGuideWithLoading() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadingTime = setTimeout(() => {
      setIsLoading(false);
    }, LOADING_DURATION);
    return () => clearTimeout(loadingTime);
  }, []);

  return (
    <div className="flex-grow h-full">
      {isLoading ? <Loading /> : <CompleteGuide />}
    </div>
  );
}

export default CompleteGuideWithLoading;
