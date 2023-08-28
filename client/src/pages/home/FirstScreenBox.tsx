import { useRef, useState } from 'react';
import TrimCardsBox from './TrimCardsBox';
import { TEXT } from './constant';
import * as Icon from '@/assets/icons';
import { TrimsDataType } from './type';

interface FirstScreenBoxProps {
  data: TrimsDataType;
}

function FirstScreenBox({ data }: FirstScreenBoxProps) {
  const secondPageRef = useRef<HTMLDivElement>(null);
  const [backgroundImgUrl, setBackgroundImgUrl] = useState(
    data.guide.backgroundImgUrl.web
  );

  return (
    <div
      className={`relative flex justify-center w-full h-screen bg-center bg-cover pt-85px transition-all duration-500`}
      style={{ backgroundImage: `url('${backgroundImgUrl}')` }}
    >
      <div className="flex flex-col justify-between max-w-5xl py-16px">
        <div>
          <p className="text-white text-24px font-hsans-head tracking-[-0.96px] leading-[31.2px]">
            {TEXT.MAKING_MY_CAR}
          </p>
          <p className="font-medium text-white text-64px font-hsans-head leading-[83.2px]">
            {data?.modelName.en.toUpperCase()}
          </p>
        </div>

        <div>
          <TrimCardsBox
            trims={data.trims}
            guide={data.guide}
            setBackgroundImgUrl={setBackgroundImgUrl}
          />
          <div className="flex flex-col items-center">
            <p className="text-white opacity-60 title mt-24px">
              {TEXT.MAIN_DETAIL_COMPARE}
            </p>

            <button
              className="relative flex flex-col items-center top-12px animate-bounce"
              onClick={() =>
                secondPageRef.current?.scrollIntoView({
                  behavior: 'smooth',
                })
              }
            >
              <Icon.MainBelowArrow opacity={0.8} />
              <Icon.MainBelowArrow
                opacity={0.4}
                className="relative bottom-18px"
              />
            </button>
          </div>
        </div>
      </div>
      <div
        className="absolute bottom-0 w-full h-80px z-[-1]"
        ref={secondPageRef}
      />
    </div>
  );
}

export default FirstScreenBox;
