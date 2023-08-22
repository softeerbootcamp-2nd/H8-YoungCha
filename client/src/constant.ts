import { BasicOptionFilterType, SelectOptionFilterType } from './types';

export const BasicOptionsId: Record<BasicOptionFilterType, number> = {
  '전체': 1,
  '성능': 1,
  '지능형 안전기술': 12,
  '안전': 13,
  '외관': 14,
  '내장': 15,
  '시트': 16,
  '편의': 17,
  '멀티미디어': 18,
};

export const SelectOptionsId: Record<SelectOptionFilterType, number> = {
  '전체': 1,
  '시스템': 7,
  '온도 관리': 8,
  '외부 장치': 9,
  '내부 장치': 10,
};

export const optionTypeName = [
  '트림',
  '파워트레인',
  '구동 방식',
  '바디 타입',
  '휠',
  '외장 색상',
  '내장 색상',
  '선택 옵션',
];
