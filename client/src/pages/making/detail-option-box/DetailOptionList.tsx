import { getPriceTemplete } from '@/utils';
import ModifyButton from '../complete/ModifyButton';
import { OptionType } from '../type';
import { Link, useParams } from 'react-router-dom';

interface DetailOptionListProps {
  option: OptionType;
}

function DetailOptionList({ option }: DetailOptionListProps) {
  const { id, mode } = useParams();
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
          {option.type === '기본 포함 품목' ? (
            <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
              기본포함
            </span>
          ) : (
            <>
              <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
                {getPriceTemplete(option.price!)}
              </span>
              <Link to={`/model/${id}/making/${mode}/${option.categoryId}`}>
                <ModifyButton onClick={() => {}} />
              </Link>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default DetailOptionList;
