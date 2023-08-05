import * as Icon from '../../assets/icons';
import BasicOptionBox from './BasicOptionBox';
import InsideColorBox from './InsideColorBox';
import MainOptionBox from './MainOptionBox';
import OutsideColorBox from './OutsideColorBox';

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
            <div>
              <div className="flex justify-between gap-[16px]">
                <div className="bg-white w-[192px] h-[123px] shrink-0">
                  trim card
                </div>
                <div className="bg-white w-[192px] h-[123px] shrink-0">
                  trim card
                </div>
                <div className="bg-white w-[192px] h-[123px] shrink-0">
                  trim card
                </div>
                <div className="bg-white w-[192px] h-[123px] shrink-0">
                  trim card
                </div>
                <div className="bg-white w-[192px] h-[123px] shrink-0">
                  trim card
                </div>
              </div>
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

      <div className="w-full h-[335px] bg-grey-001">
        <div className="max-w-7xl pt-[65px] m-auto">
          <h2 className="text-center font-hsans-head text-[40px] font-medium leading-[52px] tracking-[-1.6px] text-grey-black mb-[118px]">
            모델 한 눈에 비교하기
          </h2>
          <div className="flex justify-between px-[192px] font-hsans-head">
            <div className="flex flex-col gap-[16px] items-center">
              <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
                Exclusive
              </h3>
              <p className="body2">기본에 충실</p>
            </div>
            <div className="flex flex-col gap-[16px] items-center">
              <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
                Le Blanc (르블랑)
              </h3>
              <p className="body2">모두가 선택한 베스트셀러</p>
            </div>
            <div className="flex flex-col gap-[16px] items-center">
              <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
                Prestige
              </h3>
              <p className="body2">부담없는 고급감</p>
            </div>
            <div className="flex flex-col gap-[16px] items-center">
              <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
                Calligraphy
              </h3>
              <p className="body2">최고를 원한다면</p>
            </div>
          </div>
        </div>
      </div>

      <div className="w-full">
        <div className="max-w-7xl pt-[40px] m-auto px-[142px]">
          <ul className="flex justify-between">
            <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
              <img src="src/assets/images/palisade.png" alt="palisade" />
              <p className="font-normal body1 text-grey-black">
                38,960,000원 부터
              </p>
            </li>
            <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
              <img src="src/assets/images/palisade.png" alt="palisade" />
              <p className="font-normal body1 text-grey-black">
                38,960,000원 부터
              </p>
            </li>
            <li className=" flex flex-col items-center gap-[8px]">
              <img
                src="src/assets/images/palisade.png"
                alt="palisade"
                className="w-[214px] h-[155px]"
              />
              <p className="font-normal body1 text-grey-black">
                38,960,000원 부터
              </p>
            </li>
            <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
              <img src="src/assets/images/palisade.png" alt="palisade" />
              <p className="font-normal body1 text-grey-black">
                38,960,000원 부터
              </p>
            </li>
          </ul>

          <MainOptionBox />
          <OutsideColorBox />
          <InsideColorBox />
          <BasicOptionBox />

          <div className="flex justify-between px-[48px] mb-[69px]">
            <div className="w-[140px] h-[50px] bg-main-blue text-white rounded-[6px] flex justify-center items-center">
              내 차 만들기
            </div>
            <div className="w-[140px] h-[50px] bg-main-blue text-white rounded-[6px] flex justify-center items-center">
              내 차 만들기
            </div>
            <div className="w-[140px] h-[50px] bg-main-blue text-white rounded-[6px] flex justify-center items-center">
              내 차 만들기
            </div>
            <div className="w-[140px] h-[50px] bg-main-blue text-white rounded-[6px] flex justify-center items-center">
              내 차 만들기
            </div>
          </div>

          <div className="flex justify-center mb-[204px]">
            <div className="flex justify-center gap-[80px] bg-grey-001 w-[533px] h-[90px] items-center rounded-[6px]">
              <p className="title5 text-grey-black">
                무엇을 골라야 할 지 모르겠다면?
              </p>
              <p className="text-[32px] font-hsans-head text-grey-black font-medium tracking-[-0.96px]">
                Guide Mode
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
