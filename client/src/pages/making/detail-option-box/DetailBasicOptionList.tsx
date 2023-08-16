import { OptionType } from '../type';

interface DetailBasicOptionListProps {
  option: OptionType;
}

function DetailBasicOptionList({ option }: DetailBasicOptionListProps) {
  return (
    <div className="flex justify-between gap-43px">
      <img
        src={option.imgUrl}
        alt="palisade"
        width={200}
        height={150}
        className="rounded-6px"
      />
      <div className="flex items-center justify-between flex-1">
        <span className="text-grey-black font-hsans-text text-20px font-medium leading-[26px] tracking-[-0.6px] ">
          {option.name}
        </span>
        <div className="flex flex-col items-end gap-8px">
          <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
            기본포함
          </span>
        </div>
      </div>
    </div>
  );
}

export default DetailBasicOptionList;
