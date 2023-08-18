import { TrimType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';
import { TITLE } from './constant';

interface CarsNameListBoxProps {
  trims: TrimType[];
}

function CarsNameListBox({ trims }: CarsNameListBoxProps) {
  return (
    <>
      <div className="flex items-center justify-center h-180px bg-grey-001">
        <h2
          className={`font-hsans-head text-40px font-medium leading-52px tracking-[-1.6px] text-grey-black `}
        >
          {TITLE.CAR_NAME_LIST}
        </h2>
      </div>

      <div className="sticky flex justify-center w-full bg-grey-001 top-60px">
        <div className={`py-36px w-full max-w-5xl`}>
          <OptionLayout>
            {trims.map(({ name, description, best }, index) => (
              <li
                className="relative flex flex-col items-center w-full gap-16px"
                key={`car-name-${index}`}
              >
                {best && (
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
      </div>
    </>
  );
}

export default CarsNameListBox;
