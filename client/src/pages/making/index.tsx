import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import { INITIAL_USER_SELECTED_DATA, LAST_STEP } from './constant';
import { UserSelectedOptionDataType } from './type';
import { Dispatch, SetStateAction, createContext, useState } from 'react';

export interface UserSelectedOptionDataContextType {
  userSelectedOptionData: UserSelectedOptionDataType;
  setUserSelectedOptionData: Dispatch<
    SetStateAction<UserSelectedOptionDataType>
  >;
}

export const UserSelectedOptionDataContext =
  createContext<UserSelectedOptionDataContextType>({
    userSelectedOptionData: INITIAL_USER_SELECTED_DATA,
    setUserSelectedOptionData: () => {},
  });

function MakingPage() {
  const { step } = useParams() as { step: string };
  const { state } = useLocation();

  const [userSelectedOptionData, setUserSelectedOptionData] = useState(
    INITIAL_USER_SELECTED_DATA
  );

  return (
    <UserSelectedOptionDataContext.Provider
      value={{ userSelectedOptionData, setUserSelectedOptionData }}
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
