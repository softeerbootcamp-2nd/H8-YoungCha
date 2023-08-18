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

export type SelectOptionFilterType =
  | '전체'
  | '시스템'
  | '온도 관리'
  | '외부 장치'
  | '내부 장치';

export interface MainOptionType {
  imgUrl: string;
  name: string;
}

export interface ExteriorColorType {
  imgUrl: string;
  name: string;
}

export interface InternalColorType {
  imgUrl: string;
  name: string;
}

export interface TrimType {
  id: number;
  name: string;
  backgroundImgUrl: string;
  imgUrl: string;
  hashTag: string;
  description: string;
  best: boolean;
  price: number;
  mainOptions: MainOptionType[];
  exteriorColors: ExteriorColorType[];
  interiorColors: InternalColorType[];
}

export interface ContentsType {
  name: string;
  categoryId: number;
  imgUrl: string;
}
export interface BasicOptionType {
  first: boolean;
  last: boolean;
  totalElements: number;
  totalPages: number;
  contents: ContentsType[];
}
