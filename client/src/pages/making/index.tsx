import { useParams } from 'react-router-dom';
import CompleteOptionPage from './complete/CompleteOptionPage';
import SelectOptionPage from './SelectOptionPage';
import ProgressBar from '@/components/ProgressBar';

interface MakingProps {
  path: 'self' | 'guide';
}

const LAST_STEP = 8;

function Making({ path }: MakingProps) {
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

export default Making;
