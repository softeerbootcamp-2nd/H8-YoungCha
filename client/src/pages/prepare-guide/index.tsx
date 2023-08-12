import AgeBox from './AgeBox';

type pathType = 'age' | 'gender' | 'keyword';
interface PrepareGuideProps {
  path: pathType;
}

const innerBox: Record<pathType, React.ReactNode> = {
  age: <AgeBox />,
  gender: <AgeBox />,
  keyword: <AgeBox />,
};

function PrepareGuide({ path }: PrepareGuideProps) {
  return (
    <div className="flex justify-center pt-85px ">
      <div className="max-w-7xl bg-red-50 px-128px">{innerBox[path]}</div>
    </div>
  );
}

export default PrepareGuide;
