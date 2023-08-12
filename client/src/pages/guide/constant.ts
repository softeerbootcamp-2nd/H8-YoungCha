export const TOTAL_STEP = 3;
export enum step {
  age = 1,
  gender,
  keyword,
}

export const TEXT = {
  TITLE: {
    age: '만나서 반가워요!',
    gender: '금방 끝나요!',
    keyword: '이제 마지막이에요!',
  },
  SUB_TITLE: {
    age: '연령대를 선택해 주세요.',
    gender: '성별을 선택해 주세요.',
    keyword: '키워드를 중요한 순서로 3개 선택해주세요.',
  },
  DESCRIPTION: {
    age: '비슷한 나이대의 사람들이 선호하는 컬러를 알려드려요.',
    gender: '더 자세한 옵션을 추천해드릴 수 있어요.',
    keyword: '나의 관심사와 취향을 반영해 더 자세하게 추천해줄 수 있어요.',
  },
};
