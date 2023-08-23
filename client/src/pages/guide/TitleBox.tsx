import { TEXT } from './constant';
import { PathType } from './type';

interface TitleBoxProps {
  path: Exclude<PathType, 'complete'>;
}

function TitleBox({ path }: TitleBoxProps) {
  return (
    <div className="flex flex-col gap-16px">
      <div>
        <h1 className="font-hsans-head text-32px leading-[44.8px] tracking-[-1.28px]">
          {TEXT.TITLE[path]}
        </h1>
        <h2 className="font-hsans-head text-32px font-medium leading-[44.8px] tracking-[-1.28px]">
          {TEXT.SUB_TITLE[path]}
        </h2>
      </div>
      <pre className="text-[#8C8C8C] font-hsans-text text-20px leading-[-0.6px]">
        {TEXT.DESCRIPTION[path]}
      </pre>
    </div>
  );
}

export default TitleBox;
