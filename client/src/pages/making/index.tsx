import { useParams } from 'react-router-dom';
import CompleteOptionPage from './complete/CompleteOptionPage';
import SelectOptionPage from './select/SelectOptionPage';
import ProgressBar from '@/components/ProgressBar';
import { LAST_STEP } from './constant';
import { ModeType } from '@/types';

interface MakingPageProps {
  path: ModeType;
}

function MakingPage({ path }: MakingPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  return (
    <div className="flex flex-col w-full h-full">
      <ProgressBar step={currentStep} mode={path} id={id} />
      {currentStep < LAST_STEP ? (
        <SelectOptionPage path={path} />
      ) : (
        <CompleteOptionPage />
      )}
    </div>
  );
}

export default MakingPage;
