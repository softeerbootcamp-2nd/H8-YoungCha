import { HTMLAttributes } from 'react';

interface LoadingProps extends HTMLAttributes<HTMLDivElement> {
  isInit: boolean;
}

function LoadingStep({ children, style, isInit }: LoadingProps) {
  return (
    <div
      className={`flex items-center gap-x-12px transition duration-300 ${
        isInit ? 'opacity-100' : 'opacity-60'
      }`}
      style={style}
    >
      <div
        className="w-22px h-22px rounded-full bg-main-blue flex justify-center items-center"
        style={{ transitionDelay: 'inherit' }}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width={13}
          height={11}
          viewBox="0 0 13 11"
          style={{ transitionDelay: 'inherit' }}
        >
          <polyline
            style={{ transitionDelay: 'inherit' }}
            className={'transition-all duration-500 ease-in-out'}
            stroke="white"
            fill="none"
            strokeWidth="2"
            strokeLinecap={'round'}
            strokeLinejoin={'round'}
            strokeDashoffset={isInit ? 0 : 20}
            strokeDasharray={20}
            points="1.4,5.8 5.1,9.5 11.6,2.1 "
          />
        </svg>
      </div>
      <div
        className={`body1 transition-colors duration-300 ${
          isInit ? 'text-main-blue' : 'text-grey-004'
        }`}
      >
        {children}
      </div>
    </div>
  );
}

export default LoadingStep;
