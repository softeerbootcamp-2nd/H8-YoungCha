import { TrimType } from '@/assets/mock/mock';
import { TEXT } from './constant';
import useFetch from '@/hooks/useFetch';

import * as Icon from '../../assets/icons';

import BasicOptionBox from './BasicOptionBox';
import CarsImageBox from './CarsImageBox';
import CarsNameListBox from './CarsNameListBox';
import GuideModeButton from './GuideModeButton';
import MainOptionBox from './MainOptionBox';
import MakingCarButtonsBox from './MakingCarButtonsBox';
import TrimCardsBox from './TrimCardsBox';
import InternalColorBox from './InternalColorBox';
import ExteriorColorBox from './ExteriorColorBox';

interface TrimsDataType {
  mode: string;
  trims: TrimType[];
}
function Home() {
  // const basicOptionLists = [
  //   mockBasicOptions,
  //   mockBasicOptions,
  //   mockBasicOptions,
  //   mockBasicOptions,
  // ];

  const { data, loading } = useFetch<TrimsDataType>({
    url: `${import.meta.env.VITE_API_URL}/cars/1/details`,
  });
  return (
    <div>
      {!loading && (
        <>
          <div className="flex justify-center w-full h-screen bg-center bg-cover pt-85px bg-main-background-image">
            <div className="flex flex-col justify-between px-128px max-w-7xl py-16px">
              <div>
                <p className="text-white text-24px font-hsans-head tracking-[-0.96px] leading-[31.2px]">
                  {TEXT.MAKING_MY_CAR}
                </p>
                <p className="font-medium text-white text-64px font-hsans-head leading-[83.2px]">
                  {data?.mode}
                </p>
              </div>

              <div>
                <TrimCardsBox trims={data?.trims} />
                <div className="flex flex-col items-center">
                  <p className="text-white opacity-60 title mt-24px">
                    {TEXT.MAIN_DETAIL_COMPARE}
                  </p>

                  <div className="relative flex flex-col items-center top-12px animate-bounce">
                    <Icon.MainBelowArrow opacity={0.8} />
                    <Icon.MainBelowArrow
                      opacity={0.4}
                      className="relative bottom-18px"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="w-full pb-400px">
            <CarsNameListBox trims={data?.trims} />
            <div className="flex flex-col m-auto max-w-7xl pt-32px gap-60px px-128px">
              <CarsImageBox trims={data?.trims} />
              <div className="flex flex-col gap-48px">
                <MainOptionBox trims={data?.trims} />
                <ExteriorColorBox trims={data?.trims} />
                <InternalColorBox trims={data?.trims} />
                <BasicOptionBox />
                <MakingCarButtonsBox />
                <GuideModeButton />
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Home;
