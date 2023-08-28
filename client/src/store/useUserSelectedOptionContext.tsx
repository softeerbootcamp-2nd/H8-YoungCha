import useSelectOption from '@/hooks/useSelectOption';
import { INITIAL_USER_SELECTED_DATA } from '@/pages/making/constant';
import { OptionType, UserSelectedOptionDataType } from '@/pages/making/type';
import { createContext } from 'react';

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
  initData: () => void;
}

export const UserSelectedOptionDataContext =
  createContext<UserSelectedOptionDataContextType>({
    userSelectedOptionData: INITIAL_USER_SELECTED_DATA,
    setUserSelectedOptionData: () => {},
    saveOptionData: () => {},
    initData: () => {},
  });

function UserSelectedOptionDataContextProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const {
    userSelectedOptionData,
    setUserSelectedOptionData,
    saveOptionData,
    initData,
  } = useSelectOption();
  return (
    <UserSelectedOptionDataContext.Provider
      value={{
        userSelectedOptionData,
        setUserSelectedOptionData,
        saveOptionData,
        initData,
      }}
    >
      {children}
    </UserSelectedOptionDataContext.Provider>
  );
}

export default UserSelectedOptionDataContextProvider;
