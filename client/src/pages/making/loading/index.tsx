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
    setTimeout(() => {
      // TODO: 실제 사용시 navigate 코드 추가
    }, 3000);
  }, []);

  return (
    // TODO: h-screen 적용시 새 스크롤이 생기고, html 태그 크기가 full이 아니므로 h-screen이 적용되지 않음. 이에 대한 처리 논의 필요
    <div className="flex flex-col items-center justify-center h-full gap-y-40px">
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
