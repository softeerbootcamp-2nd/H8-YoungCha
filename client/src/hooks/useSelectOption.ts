import { INITIAL_USER_SELECTED_DATA } from '@/pages/making/constant';
import { OptionType } from '@/pages/making/type';
import getNewUserOptionData from '@/utils/getNewOptionData';
import { useEffect, useState } from 'react';
import { UserSelectedOptionDataType } from '../pages/making/type';
import { getStorage, setStorage } from '@/utils/optionStorage';
import getNewUserOptionsData from '../utils/getNewOptionsData';

// 상태 설정, storage 저장
function useSelectOption() {
  const [userSelectedOptionData, setUserSelectedOptionData] = useState(
    INITIAL_USER_SELECTED_DATA
  );

  function saveOptionData({
    newOption,
    newOptions,
  }: {
    newOption?: OptionType;
    newOptions?: OptionType[];
  }) {
    setUserSelectedOptionData((prev) => {
      const newData = { ...prev };
      const newStorageData = newOption
        ? getNewUserOptionData({ newData, newOption })
        : getNewUserOptionsData({ newData, newOptions });
      setStorage<UserSelectedOptionDataType>({
        key: 'optionData',
        value: newStorageData,
      });
      return newStorageData;
    });
  }

  useEffect(() => {
    if (userSelectedOptionData === INITIAL_USER_SELECTED_DATA) {
      const saveData = getStorage<UserSelectedOptionDataType>({
        key: 'optionData',
        initalValue: INITIAL_USER_SELECTED_DATA,
      });
      setUserSelectedOptionData(saveData);
    }
  }, []);

  return { userSelectedOptionData, setUserSelectedOptionData, saveOptionData };
}

export default useSelectOption;
