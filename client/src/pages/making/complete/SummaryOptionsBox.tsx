import SummaryOption from './SummaryOption';
import { PropsWithChildren } from 'react';

interface SummaryOptionsBoxProps {
  title: string;
  price: number;
}
function SummaryOptionsBox({
  title,
  price,
  children,
}: PropsWithChildren<SummaryOptionsBoxProps>) {
  return (
    <div>
      <div className="flex items-center justify-between w-full h-57px bg-grey-001 rounded-[6px] px-24px">
        <span className="text-grey-black font-hsans-head text-22px font-medium leading-[28.6px] tracking-[-0.66px]">
          {title}
        </span>
        <span className="text-grey-black font-hsans-head text-24px font-medium leading-[31.2px] tracking-[-0.72px]">
          {`+${price.toLocaleString()}원`}
        </span>
      </div>
      <div className="py-16px">{children}</div>
    </div>
  );
}

SummaryOptionsBox.Option = SummaryOption;
export default SummaryOptionsBox;
