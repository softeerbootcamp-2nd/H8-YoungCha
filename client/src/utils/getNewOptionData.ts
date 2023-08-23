import { OptionType, UserSelectedOptionDataType } from '@/pages/making/type';

function getNewUserOptionData({
  newData,
  newOption,
}: {
  newData: UserSelectedOptionDataType;
  newOption: OptionType;
}) {
  switch (newOption.categoryId) {
    case 1:
      newData.mainOptions.options.powerTrain = newOption;
      break;
    case 2:
      newData.mainOptions.options.drivingSystem = newOption;
      break;
    case 3:
      newData.mainOptions.options.bodyType = newOption;
      break;
    case 6:
      newData.mainOptions.options.wheel = newOption;
      break;
    case 5:
      newData.colors.options.interiorColor = newOption;
      break;
    case 4:
      newData.colors.options.exteriorColor = newOption;
      break;
    case 7:
      newData.selectedOptions.options.push(newOption);
      break;
    default:
      break;
  }
  return newData;
}

export default getNewUserOptionData;
