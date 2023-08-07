import * as Icon from '../../assets/icons';
import BasicOptionBox from './BasicOptionBox';
import CarsImageBox from './CarsImageBox';
import CarsNameListBox from './CarsNameListBox';
import GuideModeButton from './GuideModeButton';
import InsideColorBox from './InsideColorBox';
import MainOptionBox from './MainOptionBox';
import MakingCarButtonsBox from './MakingCarButtonsBox';
import OutsideColorBox from './OutsideColorBox';
import TrimCarsBox from './TrimCardsBox';

const TEXT_MAKING_MY_CAR = '내 차 만들기';
const TEXT_PALISADE = 'PALISADE';
const TEXT_MAIN_DETAIL_COMPARE = '자세한 설명과 비교를 원한다면';

const carsNameLists = [
  { name: 'Exclusive', description: '기본에 충실' },
  { name: 'Exclusive2', description: '기본에 충실' },
  { name: 'Exclusive3', description: '기본에 충실' },
  { name: 'Exclusive4', description: '기본에 충실' },
];

const carsImageLists = [
  { imgUrl: 'src/assets/images/palisade.png', minPrice: '38,960,000' },
  { imgUrl: 'src/assets/images/palisade.png', minPrice: '38,960,001' },
  { imgUrl: 'src/assets/images/palisade.png', minPrice: '38,960,002' },
  { imgUrl: 'src/assets/images/palisade.png', minPrice: '38,960,003' },
];

const MainOptionLists = [
  [
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.3인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.4인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.5인치 내비게이션',
    },
  ],
  [
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.6인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.7인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.8인치 내비게이션',
    },
  ],
  [
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.9인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.0인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.1인치 내비게이션',
    },
  ],
  [
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '12.2인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '13.3인치 내비게이션',
    },
    {
      imgUrl: 'src/assets/icons/main-navigation-option.svg',
      description: '14.3인치 내비게이션',
    },
  ],
];

const outsideColorLists = [
  [
    {
      code: '#FAFAFA',
      description: '크리미 화이트 펄',
    },
    {
      code: '#111111',
      description: '크리미 화이트 펄1',
    },
  ],
  [
    {
      code: '#FAFAFA',
      description: '크리미 화이트 펄2',
    },
    {
      code: '#111111',
      description: '크리미 화이트 펄13',
    },
  ],
  [
    {
      code: '#FAFAFA',
      description: '크리미 화이트 펄45',
    },
    {
      code: '#111111',
      description: '크리미 화이트 펄15',
    },
  ],
  [
    {
      code: '#FAFAFA',
      description: '크리미 화이트 펄4',
    },
    {
      code: '#111111',
      description: '크리미 화이트 펄15',
    },
  ],
];

const insideColorLists = [
  [
    {
      url: 'src/assets/images/black-leather.png',
      description: '인조가죽(블랙)',
    },
  ],
  [
    {
      url: 'src/assets/images/black-leather.png',
      description: '인조가죽(블랙)2',
    },
  ],
  [
    {
      url: 'src/assets/images/black-leather.png',
      description: '인조가죽(블랙)45',
    },
  ],
  [
    {
      url: 'src/assets/images/black-leather.png',
      description: '인조가죽(블랙)4',
    },
  ],
];

const basicOptionLists = [
  [
    {
      name: '8단 자동변속기',
      categoryId: 0,
      imgUrl: 'src/assets/images/8-auto-changer.png',
    },
  ],
  [
    {
      name: '8단 자동변속기',
      categoryId: 1,
      imgUrl: 'src/assets/images/8-auto-changer.png',
    },
  ],
  [
    {
      name: '8단 자동변속기',
      categoryId: 2,
      imgUrl: 'src/assets/images/8-auto-changer.png',
    },
  ],
  [
    {
      name: '8단 자동변속기',
      categoryId: 3,
      imgUrl: 'src/assets/images/8-auto-changer.png',
    },
  ],
];
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

              <div className="relative flex flex-col items-center top-[12px] animate-bounce">
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

      <div className="w-full mb-[400px]">
        <CarsNameListBox datas={carsNameLists} />
        <div className="max-w-7xl pt-[32px] m-auto flex flex-col gap-[60px] px-[128px]">
          <CarsImageBox datas={carsImageLists} />
          <div className="flex flex-col gap-[48px]">
            <MainOptionBox datas={MainOptionLists} />
            <OutsideColorBox datas={outsideColorLists} />
            <InsideColorBox datas={insideColorLists} />
            <BasicOptionBox datas={basicOptionLists} />
            <MakingCarButtonsBox />
            <GuideModeButton />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
