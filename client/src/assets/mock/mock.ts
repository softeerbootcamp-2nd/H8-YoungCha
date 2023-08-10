// TODO: 실제 데이터로 변경

export interface MainOptionType {
  imgUrl: string;
  description: string;
}

export interface ExteriorColorType {
  code: string;
  name: string;
}

export interface InternalColorType {
  url: string;
  name: string;
}

export interface TrimType {
  id: number;
  name: string;
  backgroundImgUrl: string;
  imgUrl: string;
  hashtag: string;
  description: string;
  isBest: boolean;
  minPrice: number;
  mainOptions: MainOptionType[];
  exteriorColor: ExteriorColorType[];
  internalColor: InternalColorType[];
}

export const mockLeblanc: TrimType = {
  id: 1,
  name: 'LeBlanc',
  backgroundImgUrl: '/src/assets/mock/images/main-background-car.png',
  imgUrl: '/src/assets/mock/images/palisade.png',
  hashtag: '#베스트셀러',
  description: '모두가 선택한 베스트셀러',
  isBest: true,
  minPrice: 10000000,
  mainOptions: [
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      description: '20인치\n 알로이 휠',
    },
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      description: '20인치\n 알로이 휠',
    },
    {
      imgUrl: '/src/assets/mock/icons/main-navigation-option.svg',
      description: '20인치\n 알로이 휠',
    },
  ],
  exteriorColor: [
    {
      code: '#ffffff',
      name: '펄화이트',
    },
    {
      code: '#000000',
      name: '펄블랙',
    },
    {
      code: '#ff0000',
      name: '펄레드',
    },
  ],
  internalColor: [
    {
      url: '/src/assets/mock/images/black-leather.png',
      name: '블랙',
    },
    {
      url: '/src/assets/mock/images/black-leather.png',
      name: '블랙',
    },
    {
      url: '/src/assets/mock/images/black-leather.png',
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

export const mockBasicOptions = {
  data: {
    first: true,
    last: false,
    totalElements: 1,
    totalPages: 1,
    contents: [
      {
        name: '8단 자동변속기',
        categoryId: 1,
        imgUrl: '/src/assets/mock/images/8-auto-changer.png',
      },
    ],
  },
};
