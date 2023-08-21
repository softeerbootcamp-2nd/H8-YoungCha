import { Outlet, useParams } from 'react-router-dom';
import ProgressBar from '@/components/ProgressBar';

function MakingPageLayout() {
  const { mode, step, id } = useParams() as {
    mode: 'self' | 'guide';
    step: string;
    id: string;
  };

  if (mode !== 'self' && mode !== 'guide')
    throw new Error('mode is not self or guide');

  return (
    <div className="min-h-full pt-80px min-w-768px flex flex-col">
      <ProgressBar step={Number(step)} mode={mode} id={id} />
      <Outlet />
    </div>
  );
}

export default MakingPageLayout;
