import { INITIAL_USER_SELECTED_DATA } from '@/pages/making/constant';
import { OptionType } from '@/pages/making/type';
import getNewUserOptionData from '@/utils/getNewOptionData';
import { useEffect, useState } from 'react';

// 상태 설정, storage 저장
function useSelectOption() {
  const [userSelectedOptionData, setUserSelectedOptionData] = useState(
    INITIAL_USER_SELECTED_DATA
  );

  function saveOptionData({ newOption }: { newOption: OptionType }) {
    setUserSelectedOptionData((prev) => {
      const newData = { ...prev };
      const newStorageData = getNewUserOptionData({
        newData,
        newOption,
      });
      sessionStorage.setItem('optionData', JSON.stringify(newStorageData));
      return newStorageData;
    });
  }

  useEffect(() => {
    if (userSelectedOptionData === INITIAL_USER_SELECTED_DATA) {
      const saveData = sessionStorage.getItem('optionData')
        ? JSON.parse(sessionStorage.getItem('optionData')!)
        : INITIAL_USER_SELECTED_DATA;
      setUserSelectedOptionData(saveData);
    }
  }, []);

  return { userSelectedOptionData, setUserSelectedOptionData, saveOptionData };
}

export default useSelectOption;
