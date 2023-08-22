import { ColorTextType, ColorType } from './type';

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
