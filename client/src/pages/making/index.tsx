import { useLocation, useParams } from 'react-router-dom';
import SelectOptionPage from './select/SelectOptionPage';
import CompleteOptionPage from './complete/CompleteOptionPage';
import CompleteOptionPageWithLoading from './complete/CompleteOptionPageWithLoading';
import { LAST_STEP } from './constant';

function MakingPage() {
  const { step } = useParams() as { step: string };
  const { state } = useLocation();

  if (Number(step) === LAST_STEP)
    return state?.isGuide ? (
      <CompleteOptionPage />
    ) : (
      <CompleteOptionPageWithLoading />
    );

  return <SelectOptionPage />;
}

export { default as MakingPageLayout } from './layout.tsx';
export default MakingPage;
