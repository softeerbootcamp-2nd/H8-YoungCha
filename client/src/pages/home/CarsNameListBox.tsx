import { TrimType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';

interface CarsNameListBoxProps {
  trims: TrimType[];
}

const TITLE = '모델 한 눈에 비교하기';

function CarsNameListBox({ trims }: CarsNameListBoxProps) {
  return (
    <>
      <div className="flex items-center justify-center bg-grey-001 h-180px">
        <h2
          className={`font-hsans-head text-40px font-medium leading-52px tracking-[-1.6px] text-grey-black `}
        >
          {TITLE}
        </h2>
      </div>

      <div
        className={`flex items-center justify-center bg-grey-001 py-[36px] sticky top-60px`}
      >
        <OptionLayout>
          {trims.map(({ name, description, isBest }, index) => (
            <li
              className="relative flex flex-col items-center w-full gap-16px"
              key={`car-name-${index}`}
            >
              {isBest && (
                <strong className="absolute title5 text-best-red top-[-8px]">
                  best
                </strong>
              )}
              <div></div>
              <h3 className="text-grey-black text-28px font-medium tracking-[-0.84px]">
                {name}
              </h3>
              <p className="body2">{description}</p>
            </li>
          ))}
        </OptionLayout>
      </div>
    </>
  );
}

export default CarsNameListBox;
