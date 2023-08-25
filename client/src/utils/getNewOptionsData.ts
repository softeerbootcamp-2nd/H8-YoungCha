import { OptionType, UserSelectedOptionDataType } from '@/pages/making/type';

function getNewUserOptionsData({
  newData,
  newOptions,
}: {
  newData: UserSelectedOptionDataType;
  newOptions: OptionType[] | undefined;
}) {
  if (!newOptions) return newData;

  newData.selectedOptions.options = [...newOptions];
  return newData;
}

export default getNewUserOptionsData;
