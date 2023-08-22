interface ProgressType {
  title: string;
  path: string;
  query?: string;
}

export const PROGRESS_LIST: ProgressType[] = [
  { title: '파워트레인', path: 'power-train' },
  { title: '구동 방식', path: 'driving-system' },
  { title: '바디 타입', path: 'body-type' },
  { title: '휠', path: 'wheel' },
  { title: '외장 색상', path: 'exterior-color' },
  {
    title: '내장 색상',
    path: 'interior-color',
    query: 'exteriorColorId',
  },
  { title: '옵션 선택', path: 'options' },
  { title: '견적 내기', path: '' },
];
