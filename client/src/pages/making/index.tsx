import { ModeType } from '@/types';
import { LAST_STEP } from './constant';
import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import ProgressBar from '@/components/ProgressBar';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import CompleteOptionPage from './complete/CompleteOptionPage';

interface MakingPageProps {
  path: ModeType;
}

function MakingPage({ path }: MakingPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);
  const { state } = useLocation();
  console.log(state);

  return (
    <div className="flex flex-col w-full h-full">
      <ProgressBar step={currentStep} mode={path} id={id} />
      {currentStep < LAST_STEP ? (
        <SelectOptionPage path={path} />
      ) : state?.isGuide ? (
        <CompleteOptionPage />
      ) : (
        <CompleteOptionPageWithLoading />
      )}
    </div>
  );
}

export default MakingPage;
