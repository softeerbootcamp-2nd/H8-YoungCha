import * as Icon from '@/assets/icons';

export const PROGRESSING_NOW = '현재 진행 중';

export const ExitPopUp = {
  title: '내 차 만들기에서 나가시겠어요?',
  ImgSrc: Icon.CryingFaceIcon,
  firstLine: '완료까지 얼마 남지 않았어요!',
  secondLine: '지금 종료하면 선택사항은 저장되지 않아요.',
  greyButtonContent: '나갈래요',
  blueButtonContent: '나가지 않을래요!',
};

export const ModelChangePopUp = {
  title: '모델을 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  firstLine: '모델을 변경하면 선택할 수 있는 옵션이 바뀌어요!',
  secondLine: '지금 변경하면 선택사항은 저장되지 않아요.',
  greyButtonContent: '변경할래요!',
  blueButtonContent: '안할래요',
};

export const RecommendationPopUp = {
  title: '추천견적을 받아보시겠어요?',
  ImgSrc: Icon.RecommendationIcon,
  firstLine: '추천견적을 받게되면 지금까지 선택했던',
  secondLine: '옵션들은 저장되지 않아요.',
  greyButtonContent: '안 받을래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToGuideModePopUp = {
  title: '[가이드 모드]로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  firstLine: '변경하면 지금까지 선택했던 옵션들은',
  secondLine: '저장되지 않아요.',
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToSelfModePopUp = {
  title: '[셀프 모드]로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  firstLine: '지금까지 선택했던 옵션들이',
  secondLine: '유지된 채로 변경됩니다.',
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToGasolinePopUp = {
  title: '[가솔린 3.8]으로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  singleLine: "[듀얼 패키지 머플러]는 '디젤 2.2'만 선택 가능해요.",
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ModeChangePopUp = {
  title: '내 차 만들기 방식 변경하기',
  ImgSrc: Icon.ModeChangeIcon,
};

export const SelfModeCard = {
  mode: '셀프 모드',
  firstLine: '가장 일반적으로 많은 사람들이 선택한',
  secondLine: '인기있는 옵션들을 보며 내 취향에 맞게 자유롭게',
  thirdLine: '옵션을 선택할 수 있어요.',
};

export const GuideModeCard = {
  mode: '가이드 모드',
  firstLine: '내 상황과 성향에 맞는 옵션들을 추천받아',
  secondLine: '나에게 딱 맞는 옵션들로 구성된 차량 견적을',
  thirdLine: '받아볼 수 있어요.',
};