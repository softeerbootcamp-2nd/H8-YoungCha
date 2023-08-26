import { ColorTextType, ColorType, UserSelectedOptionDataType } from './type';

export const LAST_STEP = 8;

export const OPTION_ORDER = [
  '파워 트레인',
  '구동방식',
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
        id: 2,
        type: '트림',
        name: 'Le Blanc(르블랑)',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 43_460_000,
      },
    },
  },

  colors: {
    title: '색상',
    options: {},
  },
  selectedOptions: {
    title: '옵션',
    options: [],
  },
};

export const INITIAL_KEYWORDS = {
  keyword1Id: '1',
  keyword2Id: '2',
  keyword3Id: '3',
  age: '2',
  gender: '0',
  exteriorColorId: '0',
} as Record<string, string>;
