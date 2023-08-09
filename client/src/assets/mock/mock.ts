// TODO: 실제 데이터로 변경
export interface Trim {
  id: number;
  name: string;
  backgroundImgUrl: string;
  imgUrl: string;
  hashtag: string;
  description: string;
  isBest: boolean;
  minPrice: number;
  mainOptions: {
    imgUrl: string;
    description: string;
  }[];
  exteriorColor: {
    code: string;
    name: string;
  }[];
  internalColor: {
    url: string;
    name: string;
  }[];
}

export const mockLeblanc: Trim = {
  id: 1,
  name: 'LeBlanc',
  backgroundImgUrl: '/mock/TrimCard/leblanc.png',
  imgUrl: '/mock/TrimCard/leblanc.png',
  hashtag: '#베스트셀러',
  description: '모두가 선택한 베스트셀러',
  isBest: true,
  minPrice: 10000,
  mainOptions: [
    {
      imgUrl: 'src/assets/mock/TrimCard/option1.svg',
      description: '20인치\n 알로이 휠',
    },
    {
      imgUrl: 'src/assets/mock/TrimCard/option2.svg',
      description: '서라운드 뷰\n 모니터',
    },
    {
      imgUrl: 'src/assets/mock/TrimCard/option3.svg',
      description: '클러스터\n (12.3인치 컬러 LCD)',
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
      url: 'src/mock/TrimCard/leblanc.png',
      name: '블랙',
    },
    {
      url: 'src/mock/TrimCard/leblanc.png',
      name: '블랙',
    },
    {
      url: 'src/mock/TrimCard/leblanc.png',
      name: '블랙',
    },
  ],
};

export const mockTrims: Trim[] = [
  mockLeblanc,
  mockLeblanc,
  mockLeblanc,
  mockLeblanc,
];
