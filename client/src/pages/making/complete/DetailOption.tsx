import SelectButton from '@/components/SelectButton';
import { useState } from 'react';

interface DetailOptionProps {
  type:
    | 'trim'
    | 'power-train'
    | 'driving-system'
    | 'body-type'
    | 'exteriorColor'
    | 'internalColor'
    | 'basic-option'
    | 'selected-option';
}

type BasicOptionFilterType =
  | '전체'
  | '성능'
  | '지능형 안전기술'
  | '안전'
  | '외관'
  | '내장'
  | '시트'
  | '편의'
  | '멀티미디어';

const BasicOptions: Array<BasicOptionFilterType> = [
  '전체',
  '성능',
  '지능형 안전기술',
  '안전',
  '외관',
  '내장',
  '시트',
  '편의',
  '멀티미디어',
];

const SelectOptions = ['전체', '시스템', '편의', '디자인', '주행'];

function DetailOption({ type }: DetailOptionProps) {
  const [selectedBasicOptionFilter, setSelectedBasicOptionFilter] =
    useState<BasicOptionFilterType>('전체');
  const [selectedSelectOptionFilter, setSelectedSelectOptionFilter] =
    useState('전체');
  return (
    <div className="flex flex-col w-full h-250px gap-14px">
      <div className="flex items-center justify-between w-full h-57px px-6px">
        <span className="text-grey-black font-hsans-head text-22px font-medium leading-[28.6px] tracking-[-0.66px]">
          트림
        </span>
        <span className="text-[#36383C] font-hsans-head text-24px font-medium leading-[31.2px] tracking-[-0.72px]">
          43,460,000원
        </span>
      </div>
      <div>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="1024"
          height="2"
          viewBox="0 0 1024 2"
          fill="none"
        >
          <path d="M0 1H1024" stroke="#A5ACB8" />
        </svg>
      </div>

      <div className="flex gap-8px">
        {type === 'basic-option' &&
          BasicOptions.map((option) => (
            <SelectButton
              key={option}
              type={selectedBasicOptionFilter === option ? 'active' : 'default'}
              onClick={() => setSelectedBasicOptionFilter(option)}
            >
              {option}
            </SelectButton>
          ))}
        {type === 'selected-option' &&
          SelectOptions.map((option) => (
            <SelectButton
              key={option}
              type={
                selectedSelectOptionFilter === option ? 'active' : 'default'
              }
              onClick={() => setSelectedSelectOptionFilter(option)}
            >
              {option}
            </SelectButton>
          ))}
      </div>
      <div className="flex justify-between gap-43px">
        <img
          src="/src/assets/mock/images/palisade.png"
          alt="palisade"
          width={200}
          height={150}
          className="rounded-6px"
        />
        <div className="flex items-center justify-between flex-1">
          <span className="text-grey-black font-hsans-text text-20px font-medium leading-[26px] tracking-[-0.6px] ">
            르블랑
          </span>
          <div className="flex flex-col items-end gap-8px">
            <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
              + 43,460,000원
            </span>

            <button className="flex gap-6px">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
              >
                <path
                  d="M5.345 13.3332L13.7967 4.88157L12.6183 3.70324L4.16667 12.1549V13.3332H5.345ZM6.03583 14.9999H2.5V11.4641L12.0292 1.93491C12.1854 1.77868 12.3974 1.69092 12.6183 1.69092C12.8393 1.69092 13.0512 1.77868 13.2075 1.93491L15.565 4.29241C15.7212 4.44868 15.809 4.6606 15.809 4.88157C15.809 5.10254 15.7212 5.31447 15.565 5.47074L6.03583 14.9999V14.9999ZM2.5 16.6666H17.5V18.3332H2.5V16.6666Z"
                  fill="#4CA7CE"
                />
              </svg>
              <span className="text-sub-blue font-hsans-text text-18px leading-[23.4px] tracking-[-0.54px]">
                수정
              </span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DetailOption;
