import * as Icon from '@/assets/icons';

export const SELF_MODE = '셀프 모드';
export const GUIDE_MODE = '가이드 모드';
export const PROGRESSING_NOW = '현재 진행 중';

export const ExitPopUp = {
  title: '내 차 만들기에서 나가시겠어요?',
  ImgSrc: Icon.CryingFaceIcon,
  description:
    '완료까지 얼마 남지 않았어요!\n지금 종료하면 선택사항은 저장되지 않아요.',
  greyButtonContent: '나갈래요',
  blueButtonContent: '나가지 않을래요!',
};

export const ModelChangePopUp = {
  title: '모델을 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  description:
    '모델을 변경하면 선택할 수 있는 옵션이 바뀌어요!\n지금 변경하면 선택사항은 저장되지 않아요.',
  greyButtonContent: '변경할래요!',
  blueButtonContent: '안할래요',
};

export const RecommendationPopUp = {
  title: '추천견적을 받아보시겠어요?',
  ImgSrc: Icon.RecommendationIcon,
  description:
    '추천견적을 받게되면 지금까지 선택했던\n옵션들은 저장되지 않아요.',
  greyButtonContent: '안 받을래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToGuideModePopUp = {
  title: '[가이드 모드]로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  description: '변경하면 지금까지 선택했던 옵션들은\n저장되지 않아요.',
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToSelfModePopUp = {
  title: '[셀프 모드]로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  description: '지금까지 선택했던 옵션들이\n유지된 채로 변경됩니다.',
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ChangeToGasolinePopUp = {
  title: '[가솔린 3.8]으로 변경하시겠어요?',
  ImgSrc: Icon.ModelChangeIcon,
  description: "[듀얼 패키지 머플러]는 '디젤 2.2'만 선택 가능해요.",
  greyButtonContent: '안할래요',
  blueButtonContent: '변경할래요!',
};

export const ModeChangePopUp = {
  title: '내 차 만들기 방식 변경하기',
  ImgSrc: Icon.ModeChangeIcon,
};

export const SelfModeCard = {
  mode: '셀프 모드',
  description:
    '가장 일반적으로 많은 사람들이 선택한\n인기있는 옵션들을 보며 내 취향에 맞게 자유롭게\n옵션을 선택할 수 있어요.',
};

export const GuideModeCard = {
  mode: '가이드 모드',
  description:
    '내 상황과 성향에 맞는 옵션들을 추천받아\n나에게 딱 맞는 옵션들로 구성된 차량 견적을\n받아볼 수 있어요.',
};

export const MODEL_LIST = [
  '수소/전기차',
  'N',
  '승용',
  'SUV',
  'MPV',
  '소형트럭&택시',
  '트럭',
  '버스',
];

export const ModelListPopUpText = {
  description: '변경을 원하는 모델을 선택해주세요.',
  greyButtonContent: '취소',
  blueButtonContent: '선택완료',
};

export const SpecificModelChangePopUpTitle = {
  title: '로 변경하시겠어요?',
};
