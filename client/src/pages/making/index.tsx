import { createContext, useContext } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { SelectOptionPage, SelectMultiOptionPage } from './select';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import useSelectOption from '@/hooks/useSelectOption.ts';
import { INITIAL_USER_SELECTED_DATA, LAST_STEP } from './constant';
import { OptionType, UserSelectedOptionDataType } from './type';
import { PathParamsType } from '@/types/router.ts';
import useFetch from '@/hooks/useFetch.ts';
import { AllOptionType } from '@/types/option.ts';
import {
  INTERIOR_COLOR_STEP,
  PROGRESS_LIST,
} from '@/pages/making/select/constant.ts';

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
  const { data, loading: isLoading } = useFetch<AllOptionType[]>({
    url,
    params:
      mode === 'guide'
        ? ({
            keyword1Id: '1',
            keyword2Id: '2',
            keyword3Id: '3',
            age: '2',
            gender: '0',
            exteriorColorId:
              Number(step) === INTERIOR_COLOR_STEP
                ? userSelectedOptionData.colors.options.exteriorColor?.id.toString()
                : '0',
          } as Record<string, string>)
        : Number(step) === INTERIOR_COLOR_STEP
        ? ({
            exteriorColorId:
              userSelectedOptionData.colors.options.exteriorColor?.id.toString(),
          } as Record<string, string>)
        : undefined,
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
