import { useEffect, useState } from 'react';
import {
  LOADING_DESCRIPTION,
  LOADING_STEPS,
} from '@/pages/making/loading/constant.ts';
import LoadingCircle from '@/pages/making/loading/LoadingCircle.tsx';
import LoadingIcons from '@/pages/making/loading/LoadingIcons.tsx';
import LoadingStep from '@/pages/making/loading/LoadingStep.tsx';

function Loading() {
  const [isInit, setIsInit] = useState(false);
  useEffect(() => {
    setIsInit(true);
  }, []);

  return (
    <div className="flex flex-col items-center justify-center h-full gap-y-40px flex-grow">
      <LoadingCircle isInit={isInit}>
        <LoadingIcons />
      </LoadingCircle>
      <h3 className="text-center whitespace-pre-line title1 text-main-blue">
        {LOADING_DESCRIPTION}
      </h3>
      <div className={'flex flex-col gap-y-20px'}>
        {LOADING_STEPS.map((step, index) => (
          <LoadingStep
            isInit={isInit}
            key={step}
            style={{ transitionDelay: `${index}.5s` }}
          >
            {step}
          </LoadingStep>
        ))}
      </div>
    </div>
  );
}

export default Loading;
