import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import { INITIAL_USER_SELECTED_DATA, LAST_STEP } from './constant';
import { OptionType, UserSelectedOptionDataType } from './type';
import { createContext } from 'react';
import useSelectOption from '@/hooks/useSelectOption.ts';

export interface UserSelectedOptionDataContextType {
  userSelectedOptionData: UserSelectedOptionDataType;
  saveOptionData: ({ newOption }: { newOption: OptionType }) => void;
}

export const UserSelectedOptionDataContext =
  createContext<UserSelectedOptionDataContextType>({
    userSelectedOptionData: INITIAL_USER_SELECTED_DATA,
    saveOptionData: () => {},
  });

function MakingPage() {
  const { step } = useParams() as { step: string };
  const { state } = useLocation();

  const { userSelectedOptionData, saveOptionData } = useSelectOption();

  return (
    <UserSelectedOptionDataContext.Provider
      value={{
        userSelectedOptionData,
        saveOptionData,
      }}
    >
      {Number(step) !== LAST_STEP ? (
        <SelectOptionPage />
      ) : state?.isGuide ? (
        <CompleteOptionPage />
      ) : (
        <CompleteOptionPageWithLoading />
      )}
    </UserSelectedOptionDataContext.Provider>
  );
}

export { default as MakingPageLayout } from './layout.tsx';
export default MakingPage;
