import { createContext, useContext } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { SelectOptionPage, SelectMultiOptionPage } from './select';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import useSelectOption from '@/hooks/useSelectOption.ts';
import {
  INITIAL_KEYWORDS,
  INITIAL_USER_SELECTED_DATA,
  LAST_STEP,
} from './constant';
import { OptionType, UserSelectedOptionDataType } from './type';
import { PathParamsType } from '@/types/router.ts';
import useFetch from '@/hooks/useFetch.ts';
import { AllOptionType } from '@/types/option.ts';
import {
  INTERIOR_COLOR_STEP,
  PROGRESS_LIST,
} from '@/pages/making/select/constant.ts';
import { getStorage } from '@/utils/optionStorage.ts';

export interface UserSelectedOptionDataContextType {
  userSelectedOptionData: UserSelectedOptionDataType;
  setUserSelectedOptionData: (data: UserSelectedOptionDataType) => void;
  saveOptionData: ({
    newOption,
    newOptions,
  }: {
    newOption?: OptionType;
    newOptions?: OptionType[];
  }) => void;
}

export const UserSelectedOptionDataContext =
  createContext<UserSelectedOptionDataContextType>({
    userSelectedOptionData: INITIAL_USER_SELECTED_DATA,
    setUserSelectedOptionData: () => {},
    saveOptionData: () => {},
  });

function MakingPage() {
  const { step, mode } = useParams() as PathParamsType;
  const { state } = useLocation();

  if (Number(step) === LAST_STEP) {
    return state?.isGuide ? (
      <CompleteOptionPage />
    ) : (
      <CompleteOptionPageWithLoading />
    );
  }

  const { userSelectedOptionData } = useContext(UserSelectedOptionDataContext);
  const url = `/car-make/2/${mode}/${PROGRESS_LIST[Number(step) - 1].path}`;

  const getParams = () => {
    const keywords = getStorage({
      key: 'keywords',
      initalValue: INITIAL_KEYWORDS,
    });

    if (mode === 'guide') {
      return keywords;
    }
    if (mode === 'self') {
      return Number(step) === INTERIOR_COLOR_STEP
        ? ({
            exteriorColorId:
              userSelectedOptionData.colors.options.exteriorColor?.id.toString(),
          } as Record<string, string>)
        : undefined;
    }
  };
  const { data, loading: isLoading } = useFetch<AllOptionType[]>({
    url,
    params: getParams(),
  });

  if (Number(step) === LAST_STEP - 1)
    return <SelectMultiOptionPage {...{ data, isLoading }} />;

  return <SelectOptionPage {...{ data, isLoading }} />;
}

export default function MakingPageWithProvider() {
  const { userSelectedOptionData, setUserSelectedOptionData, saveOptionData } =
    useSelectOption();

  return (
    <UserSelectedOptionDataContext.Provider
      value={{
        userSelectedOptionData,
        setUserSelectedOptionData,
        saveOptionData,
      }}
    >
      <MakingPage />
    </UserSelectedOptionDataContext.Provider>
  );
}

export { default as MakingPageLayout } from './layout.tsx';
