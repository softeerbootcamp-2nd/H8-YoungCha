import { useParams } from 'react-router-dom';
import CompleteOptionPage from './complete/CompleteOptionPage';
import SelectOptionPage from './select/SelectOptionPage';
import ProgressBar from '@/components/ProgressBar';
import { LAST_STEP } from './constant';

interface MakingPageProps {
  path: 'self' | 'guide';
}

function MakingPage({ path }: MakingPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  return (
    <div className="flex flex-col w-full h-screen pt-85px">
      <ProgressBar step={currentStep} path={path} id={id} />
      {currentStep < LAST_STEP ? (
        <SelectOptionPage path={path} />
      ) : (
        <CompleteOptionPage />
      )}
    </div>
  );
}

export default MakingPage;
