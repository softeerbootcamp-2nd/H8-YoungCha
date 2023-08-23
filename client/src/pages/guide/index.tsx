import CompleteGuideWithLoading from './CompleteGuideWithLoading';
import PrepareGuide from './PrepareGuide';
import { PathType } from './type';

interface PrepareGuideProps {
  path: PathType;
}

function Guide({ path }: PrepareGuideProps) {
  return (
    <div className="flex justify-between flex-grow mx-auto max-w-7xl px-128px w-full">
      {path === 'complete' ? (
        <CompleteGuideWithLoading />
      ) : (
        <PrepareGuide path={path} />
      )}
    </div>
  );
}

export default Guide;
