import { GuideType, TrimType } from '@/types';

export type BasicOptionFilterType =
  | '전체'
  | '성능'
  | '지능형 안전기술'
  | '안전'
  | '외관'
  | '내장'
  | '시트'
  | '편의'
  | '멀티미디어';

export interface TrimsDataType {
  modelName: {
    ko: string;
    en: string;
  };
  trims: TrimType[];
  guide: GuideType;
}
