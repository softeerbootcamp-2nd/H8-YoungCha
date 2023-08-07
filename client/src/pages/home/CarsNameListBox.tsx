import OptionLayout from './OptionLayout';
import React from 'react';

type DataType = { name: string; description: string };
interface CarsNameListBoxProps {
  datas: DataType[];
}

const TITLE = '모델 한 눈에 비교하기';

function CarsNameListBox({ datas }: CarsNameListBoxProps) {
  return (
    <>
      <div className="flex items-center justify-center bg-grey-001 h-180px">
        <h2 className="font-hsans-head text-40px font-medium leading-52px tracking-[-1.6px] text-grey-black">
          {TITLE}
        </h2>
      </div>

      <div className="flex items-center justify-center bg-grey-001 py-[36px]">
        <OptionLayout>
          {datas.map(({ name, description }) => (
            <li
              className="flex flex-col items-center w-full gap-16px"
              key={name}
            >
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
