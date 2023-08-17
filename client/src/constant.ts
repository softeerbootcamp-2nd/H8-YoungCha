import { BasicOptionFilterType, SelectOptionFilterType } from './types';

export const BasicOptions: Record<BasicOptionFilterType, number> = {
  '전체': -1,
  '성능': 11,
  '지능형 안전기술': 12,
  '안전': 13,
  '외관': 14,
  '내장': 15,
  '시트': 16,
  '편의': 17,
  '멀티미디어': 18,
};

export const SelectOptions: Record<SelectOptionFilterType, number> = {
  전체: -1,
  시스템: 7,
  편의: 8,
  디자인: 9,
  주행: 10,
};
