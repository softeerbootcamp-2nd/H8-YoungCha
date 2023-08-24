// TODO: 실제 데이터로 변경

import { UserSelectedOptionDataType } from '@/pages/making/type';
import { BasicOptionType, TrimType } from '@/types';

export const mockLeblanc: TrimType = {
  id: 1,
  name: 'LeBlanc',
  backgroundImgUrl: '/src/assets/mock/images/main-background-car.png',
  imgUrl: '/src/assets/mock/images/palisade.png',
  hashTag: '#베스트셀러',
  description: '모두가 선택한 베스트셀러',
  best: true,
  price: 10000000,
  mainOptions: [
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      name: '20인치\n 알로이 휠',
    },
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      name: '20인치\n 알로이 휠',
    },
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      name: '20인치\n 알로이 휠',
    },
  ],
  exteriorColors: [
    {
      imgUrl: '#ffffff',
      name: '펄화이트',
    },
    {
      imgUrl: '#000000',
      name: '펄블랙',
    },
    {
      imgUrl: '#ff0000',
      name: '펄레드',
    },
  ],
  interiorColors: [
    {
      imgUrl: '/src/assets/mock/images/black-leather.png',
      name: '블랙',
    },
    {
      imgUrl: '/src/assets/mock/images/black-leather.png',
      name: '블랙',
    },
    {
      imgUrl: '/src/assets/mock/images/black-leather.png',
      name: '블랙',
    },
  ],
};

export const mockTrims: TrimType[] = [
  mockLeblanc,
  mockLeblanc,
  mockLeblanc,
  mockLeblanc,
];

export const mockBasicOptions: BasicOptionType = {
  first: true,
  last: false,
  totalElements: 1,
  totalPages: 1,
  contents: [
    {
      name: '8단 자동변속기',
      categoryId: 10,
      imgUrl: '/src/assets/mock/images/8-auto-changer.png',
    },
  ],
};

export const mockUserSelectedOptionData: UserSelectedOptionDataType = {
  mainOptions: {
    title: '팰리세이드 Le Blanc (르블랑)',
    options: {
      trim: {
        id: 2,
        type: '트림',
        name: 'Le Blanc',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
      },
      powerTrain: {
        id: 1,
        type: '파워트레인',
        name: '디젤 2.2',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 1,
      },
      drivingSystem: {
        id: 3,
        type: '구동 방식',
        name: '2WD',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 2,
      },
      bodyType: {
        id: 5,
        type: '바디 타입',
        name: '7인승',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 3,
      },
      wheel: {
        id: 15,
        type: '휠',
        name: '20인치',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 4,
      },
    },
  },

  colors: {
    title: '색상',
    options: {
      exteriorColor: {
        id: 12,
        type: '외장 색상',
        name: '크리미 화이트 펄',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 5,
      },
      interiorColor: {
        id: 13,
        type: '내장 색상',
        name: '퀼팅천연 (블랙)',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 6,
      },
    },
  },

  selectedOptions: {
    title: '옵션',
    options: [
      {
        id: 17,
        type: '선택 옵션',
        name: '컴포트 2',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 7,
      },
      {
        id: 18,
        type: '선택 옵션',
        name: '현대 스마트 센스 1',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 7,
      },
      {
        id: 19,
        type: '선택 옵션',
        name: '주차 보조 시스템 2',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 7,
      },
    ],
  },
};
