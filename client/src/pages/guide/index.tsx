import CompleteGuide from './CompleteGuide';
import PrepareGuide from './PrepareGuide';
import { PathType } from './type';

interface PrepareGuideProps {
  path: PathType;
}

function Guide({ path }: PrepareGuideProps) {
  return (
    <div className="w-full">
      <div className="flex justify-between flex-grow mx-auto max-w-7xl px-128px">
        {path === 'complete' ? <CompleteGuide /> : <PrepareGuide path={path} />}
      </div>
    </div>
  );
}

export default Guide;
