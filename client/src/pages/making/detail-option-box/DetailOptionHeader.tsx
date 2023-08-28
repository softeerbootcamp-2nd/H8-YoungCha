import { formatPrice } from '@/utils';
import { OptionType } from '../type';

interface DetailOptionHeaderProps {
  option: Omit<OptionType, 'name' | 'imgUrl' | 'id'>;
}

function DetailOptionHeader({ option }: DetailOptionHeaderProps) {
  return (
    <>
      <div className="flex items-center justify-between w-full px-6px">
        <span className="text-grey-black font-hsans-head text-22px font-medium leading-[28.6px] tracking-[-0.66px]">
          {option.type}
        </span>
        <span className="text-grey-black font-hsans-head text-24px font-medium leading-[31.2px] tracking-[-0.72px]">
          {formatPrice(option.price!)}
        </span>
      </div>
      <div>
        <hr />
      </div>
    </>
  );
}

export default DetailOptionHeader;
