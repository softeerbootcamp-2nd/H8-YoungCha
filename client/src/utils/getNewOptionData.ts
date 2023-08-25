import { OptionType, UserSelectedOptionDataType } from '@/pages/making/type';

const CATEGORY = [
  '',
  '파워 트레인',
  '구동방식',
  '바디 타입',
  '외장 색상',
  '내장 색상',
  '휠',
  '옵션',
];
function getNewUserOptionData({
  newData,
  newOption,
  newOptions,
}: {
  newData: UserSelectedOptionDataType;
  newOption?: OptionType;
  newOptions?: OptionType[];
}) {
  if (newOption) {
    switch (CATEGORY[newOption.categoryId!]) {
      //TODO: newOption.categoryId -> newOption.categoryName으로 변경
      //반복되는 newData.mainOptions 와 같은 것 분해할당으로 재활용
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
  } else if (newOptions) {
    newData.selectedOptions.options = newOptions;
  }
  return newData;
}

export default getNewUserOptionData;
