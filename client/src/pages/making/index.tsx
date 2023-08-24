import { createContext } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import SelectMultiOptionPage from '@/pages/making/select/SelectMultiOptionPage.tsx';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import useSelectOption from '@/hooks/useSelectOption.ts';
import { INITIAL_USER_SELECTED_DATA, LAST_STEP } from './constant';
import { OptionType, UserSelectedOptionDataType } from './type';

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

  if (Number(step) === LAST_STEP) {
    return state?.isGuide ? (
      <CompleteOptionPage />
    ) : (
      <CompleteOptionPageWithLoading />
    );
  }

  if (Number(step) === LAST_STEP - 1) {
    return <SelectMultiOptionPage />;
  }

  return <SelectOptionPage />;
}

export { default as MakingPageLayout } from './layout.tsx';
export default function MakingPageWithProvider() {
  const { userSelectedOptionData, saveOptionData } = useSelectOption();

  return (
    <UserSelectedOptionDataContext.Provider
      value={{
        userSelectedOptionData,
        saveOptionData,
      }}
    >
      <MakingPage />
    </UserSelectedOptionDataContext.Provider>
  );
}
