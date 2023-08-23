import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import { LAST_STEP } from './constant';
import { UserSelectedOptionDataType } from './type';
import { Dispatch, SetStateAction, createContext, useState } from 'react';

export interface UserSelectedOptionDataContextType {
  userSelectedOptionData: UserSelectedOptionDataType;
  setUserSelectedOptionData: Dispatch<
    SetStateAction<UserSelectedOptionDataType>
  >;
}

const INITIAL_USER_SELECTED_DATA: UserSelectedOptionDataType = {
  mainOptions: {
    title: '팰리세이드 Le Blanc (르블랑)',
    options: {
      trim: {
        type: '트림',
        name: 'Le Blanc',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
      },
      powerTrain: {
        type: '파워트레인',
        name: '디젤 2.2',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 1,
      },
      drivingSystem: {
        type: '구동 방식',
        name: '2WD',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 2,
      },
      bodyType: {
        type: '바디 타입',
        name: '7인승',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 3,
      },
      wheel: {
        type: '휠',
        name: '20인치',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 6,
      },
    },
  },

  colors: {
    title: '색상',
    options: {
      exteriorColor: {
        type: '외장 색상',
        name: '크리미 화이트 펄',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 4,
      },
      interiorColor: {
        type: '내장 색상',
        name: '퀼팅천연 (블랙)',
        imgUrl: '/src/assets/mock/images/palisade.png',
        price: 1000000,
        categoryId: 5,
      },
    },
  },

  selectedOptions: {
    title: '옵션',
    options: [],
  },
};

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
