import SelectButton from '@/components/SelectButton';
import StepBox from './StepBox';
import TitleBox from './TitleBox';
import AgeSelectButtonsBox from './SelectButtonsBox/AgeSelectButtonsBox';
import GenderSelectButtonsBox from './SelectButtonsBox/GenderSelectButtonsBox';
import KeywordSelectButtonsBox from './SelectButtonsBox/KeywordSelectButtonsBox';
import { Link } from 'react-router-dom';
import { PathType } from './type';
import { step } from './constant';

interface PrepareGuideProps {
  path: Exclude<PathType, 'complete'>;
}

const SelectButtonsBox = {
  age: <AgeSelectButtonsBox />,
  gender: <GenderSelectButtonsBox />,
  keyword: <KeywordSelectButtonsBox />,
};

function PrepareGuide({ path }: PrepareGuideProps) {
  return (
    <>
      <div className="flex flex-col mt-155px gap-24px">
        <StepBox currentStep={step[path]} />
        <TitleBox path={path} />
        {path === 'keyword' && (
          <Link to="/model/LX06/guide/complete">
            <SelectButton type="active" size="lg" flex="center">
              선택 완료
            </SelectButton>
          </Link>
        )}
      </div>

      {SelectButtonsBox[path]}
    </>
  );
}

export default PrepareGuide;
