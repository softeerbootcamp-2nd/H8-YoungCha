import { OptionType, UserSelectedOptionDataType } from '@/pages/making/type';

const CATEGORY = [
  '',
  '파워 트레인',
  '구동방식',
  '바디 타입',
  '외장 색상',
  '내장 색상',
  '휠',
];
function getNewUserOptionData({
  newData,
  newOption,
}: {
  newData: UserSelectedOptionDataType;
  newOption: OptionType;
}) {
  switch (CATEGORY[newOption.categoryId!]) {
    case '파워 트레인':
      newData.mainOptions.options.powerTrain = newOption;
      break;
    case '구동방식':
      newData.mainOptions.options.drivingSystem = newOption;
      break;
    case '바디 타입':
      newData.mainOptions.options.bodyType = newOption;
      break;
    case '외장 색상':
      newData.colors.options.exteriorColor = newOption;
      break;
    case '내장 색상':
      newData.colors.options.interiorColor = newOption;
      break;
    case '휠':
      newData.mainOptions.options.wheel = newOption;
      break;
  }
  return newData;
}

export default getNewUserOptionData;
