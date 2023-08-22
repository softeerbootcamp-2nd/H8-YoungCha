import { useParams } from 'react-router-dom';
import CompleteOptionPage from './complete/CompleteOptionPage';
import SelectOptionPage from './select/SelectOptionPage';
import { LAST_STEP } from './constant';

function MakingPage() {
  const { mode, step } = useParams() as {
    mode: 'self' | 'guide';
    step: string;
  };

  if (Number(step) === LAST_STEP) return <CompleteOptionPage />;

  return <SelectOptionPage path={mode} />;
}

export { default as MakingPageLayout } from './layout.tsx';
export default MakingPage;
