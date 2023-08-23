import { ColorTextType, ColorType, UserSelectedOptionDataType } from './type';

export const LAST_STEP = 8;

export const OPTION_ORDER = [
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '휠',
  '외장 색상',
  '내장 색상',
  '옵션',
];

export const COMPLETE_OPTION_PAGE_TITLE =
  '나를 위한 팰리세이드가\n완성되었어요!';

export const CAR_COLOR: { text: ColorTextType; type: ColorType }[] = [
  { text: '외부', type: 'exterior' },
  { text: '내부', type: 'interior' },
];

export const INITIAL_USER_SELECTED_DATA: UserSelectedOptionDataType = {
  mainOptions: {
    title: '팰리세이드 Le Blanc (르블랑)',
    options: {
      trim: {
        type: '트림',
        name: 'Le Blanc',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 43_460_000,
      },
      powerTrain: {
        type: '파워트레인',
        name: '디젤 2.2',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 1,
      },
      drivingSystem: {
        type: '구동 방식',
        name: '2WD',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 2,
      },
      bodyType: {
        type: '바디 타입',
        name: '7인승',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 3,
      },
      wheel: {
        type: '휠',
        name: '20인치',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 6,
      },
    },
  },

  colors: {
    title: '색상',
    options: {
      exteriorColor: {
        type: '외장 색상',
        name: '크리미 화이트 펄',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 4,
      },
      interiorColor: {
        type: '내장 색상',
        name: '퀼팅천연 (블랙)',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 5,
      },
    },
  },

  selectedOptions: {
    title: '옵션',
    options: [],
  },
};
