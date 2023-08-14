import { HTMLAttributes } from 'react';
import {
  LOADING_CIRCLE_CIRCUMFERENCE,
  LOADING_CIRCLE_RADIUS,
} from '@/pages/making/loading/constant.ts';

interface LoadingCircleProps extends HTMLAttributes<HTMLDivElement> {
  isInit: boolean;
}

function LoadingCircle({ children, isInit }: LoadingCircleProps) {
  const ratio = isInit ? 1 : 0;
  return (
    <>
      <div className="relative w-180px h-180px">
        <div className="absolute -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2">
          {children}
        </div>
        <svg
          width={180}
          height={180}
          viewBox="0 0 180 180"
          className="absolute"
        >
          <circle
            cx={90}
            cy={90}
            r={LOADING_CIRCLE_RADIUS}
            stroke="currentColor"
            strokeWidth={6}
            strokeLinecap="round"
            className="text-grey-002"
            fill="none"
          />
          <circle
            cx={90}
            cy={90}
            r={LOADING_CIRCLE_RADIUS}
            stroke="currentColor"
            strokeWidth={7}
            strokeLinecap="round"
            strokeDashoffset={LOADING_CIRCLE_CIRCUMFERENCE * 0.25}
            strokeDasharray={`${LOADING_CIRCLE_CIRCUMFERENCE * ratio}, ${
              LOADING_CIRCLE_CIRCUMFERENCE * (1 - ratio)
            }`}
            className="transition-all duration-[3s] ease-in text-main-blue"
            fill="none"
          />
        </svg>
      </div>
    </>
  );
}

export default LoadingCircle;
