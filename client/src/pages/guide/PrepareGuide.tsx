import StepBox from './StepBox';
import TitleBox from './TitleBox';
import AgeSelectButtonsBox from './SelectButtonsBox/AgeSelectButtonsBox';
import GenderSelectButtonsBox from './SelectButtonsBox/GenderSelectButtonsBox';
import KeywordSelectButtonsBox from './SelectButtonsBox/KeywordSelectButtonsBox';
import { PathType } from './type';
import { step } from './constant';
import { TagSelectProvider } from '@/store/useTagSelectContext';
import MoveButtonBox from './MoveButtonBox';

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
    <TagSelectProvider>
      <div className="flex flex-col mt-155px gap-24px">
        <StepBox currentStep={step[path]} />
        <TitleBox path={path} />
        <div className="flex gap-8px">
          <MoveButtonBox path={path} />
        </div>
      </div>
      {SelectButtonsBox[path]}
    </TagSelectProvider>
  );
}

export default PrepareGuide;
