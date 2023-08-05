import * as Icon from '../../assets/icons';
import BasicOptionBox from './BasicOptionBox';
import CarsImageBox from './CarsImageBox';
import CarsNameListBox from './CarsNameList';
import GuideModeButton from './GuideModeButton';
import InsideColorBox from './InsideColorBox';
import MainOptionBox from './MainOptionBox';
import MakingCarButtonsBox from './MakingCarButtonsBox';
import OutsideColorBox from './OutsideColorBox';
import TrimCarsBox from './TrimCardsBox';

const TEXT_MAKING_MY_CAR = '내 차 만들기';
const TEXT_PALISADE = 'PALISADE';
const TEXT_MAIN_DETAIL_COMPARE = '자세한 설명과 비교를 원한다면';

function Home() {
  return (
    <div>
      <div className="w-full h-screen pt-[85px] bg-center bg-cover bg-main-background-image flex justify-center">
        <div className="px-[128px] max-w-7xl flex flex-col justify-between py-[16px]">
          <div>
            <p className="text-white text-[24px] font-hsans-head tracking-[-0.96px] leading-[31.2px]">
              {TEXT_MAKING_MY_CAR}
            </p>
            <p className="text-white text-[64px] font-hsans-head leading-[83.2px] font-medium">
              {TEXT_PALISADE}
            </p>
          </div>

          <div>
            <TrimCarsBox />
            <div>
              <p className="text-center text-white opacity-60 title mt-[24px]">
                {TEXT_MAIN_DETAIL_COMPARE}
              </p>

              <div className="flex flex-col items-center ">
                <Icon.MainBelowArrow opacity={0.8} />
                <Icon.MainBelowArrow
                  opacity={0.4}
                  className="relative bottom-[18px]"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="w-full">
        <CarsNameListBox />
        <div className="max-w-7xl pt-[40px] m-auto px-[142px]">
          <CarsImageBox />
          <MainOptionBox />
          <OutsideColorBox />
          <InsideColorBox />
          <BasicOptionBox />
          <MakingCarButtonsBox />
          <GuideModeButton />
        </div>
      </div>
    </div>
  );
}

export default Home;
