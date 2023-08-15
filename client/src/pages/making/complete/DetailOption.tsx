import SelectButton from '@/components/SelectButton';
import { BasicOptions, SelectOptions } from '@/constant';
import { BasicOptionFilterType, SelectOptionFilterType } from '@/types';
import { useState } from 'react';
import ModifyButton from './ModifyButton';

type DetailOptionType =
  | 'trim'
  | 'power-train'
  | 'driving-system'
  | 'body-type'
  | 'exteriorColor'
  | 'internalColor'
  | 'basic-option'
  | 'selected-option';

interface DetailOptionProps {
  type: DetailOptionType;
}

function DetailOption({ type }: DetailOptionProps) {
  const [selectedBasicOptionFilter, setSelectedBasicOptionFilter] =
    useState<BasicOptionFilterType>('전체');
  const [selectedSelectOptionFilter, setSelectedSelectOptionFilter] =
    useState<SelectOptionFilterType>('전체');

  return (
    <div className="flex flex-col w-full gap-12px">
      <div className="flex items-center justify-between w-full px-6px">
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
            {type === 'basic-option' ? (
              <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
                기본포함
              </span>
            ) : (
              <>
                <span className="text-grey-black font-hsans-head text-20px leading-[26px] tracking-[-0.6px]">
                  + 43,460,000원
                </span>
                <ModifyButton onClick={() => {}} />
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default DetailOption;
