import SummaryOption from './SummaryOption';
import { PropsWithChildren } from 'react';

interface SummaryOptionsBoxProps {
  title: string;
  price: string;
}
function SummaryOptionsBox({
  title,
  price,
}: PropsWithChildren<SummaryOptionsBoxProps>) {
  return (
    <div>
      <div className="flex items-center justify-between w-full h-57px bg-[#f7f7f7] rounded-[6px] px-24px">
        <span className="text-grey-black font-hsans-head text-22px font-medium leading-[28.6px] tracking-[-0.66px]">
          {title}
        </span>
        <span className="text-[#36383C] font-hsans-head text-24px font-medium leading-[31.2px] tracking-[-0.72px]">
          {`+${price}원`}
        </span>
      </div>
      <div className="py-16px">
        <SummaryOption type="파워트레인" name="디젤 2.2" price="2,123,000" />
        <SummaryOption name="디젤 2.2" price="2,123,000" />
      </div>
    </div>
  );
}

SummaryOptionsBox.Option = SummaryOption;
export default SummaryOptionsBox;
