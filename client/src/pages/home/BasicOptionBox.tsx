import { BasicOptionType } from '@/assets/mock/mock';
import OptionLayout from './OptionLayout';
import SelectButton from '@/components/SelectButton';
import { useState } from 'react';
import Button from '@/components/Button';
import { MoreViewArrow } from '@/assets/icons';

interface BasicOptionBoxProps {
  basicOptionLists: BasicOptionType[];
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

const TITLE = '기본 포함 품목';

function BasicOptionBox({ basicOptionLists }: BasicOptionBoxProps) {
  const [selectedOption, setSelectedOption] =
    useState<BasicOptionFilterType>('전체');
  const [currentPage, setCurrentPage] = useState(1);

  function handleMoreOptionClick() {
    setCurrentPage((prev) => prev + 1);
  }
  return (
    <div className="flex flex-col gap-16px max-w-7xl">
      <h3 className="font-medium text-center text-grey-black py-8px">
        {TITLE}
      </h3>
      <div className="flex flex-col items-center gap-32px">
        <div className="flex justify-center gap-8px">
          {BasicOptions.map((option) => (
            <SelectButton
              key={option}
              type={selectedOption === option ? 'active' : 'default'}
              onClick={() => setSelectedOption(option)}
            >
              {option}
            </SelectButton>
          ))}
        </div>
        <OptionLayout>
          {basicOptionLists.map(({ contents }, index) => (
            <ul
              className="flex flex-col w-full gap-24px"
              key={`basic-option-${index}`}
            >
              {contents.map(({ name, imgUrl }, index) => (
                <li
                  className="flex items-center gap-15px"
                  key={`basic-option-content-${index}`}
                >
                  <img
                    src={imgUrl}
                    alt={name}
                    className="w-80px h-60px rounded-6px"
                  />
                  <p className="font-medium body2">{name}</p>
                </li>
              ))}
            </ul>
          ))}
        </OptionLayout>
        {currentPage < 4 && (
          <Button color="grey" onClick={handleMoreOptionClick}>
            더보기
            <MoreViewArrow />
          </Button>
        )}
      </div>
    </div>
  );
}

export default BasicOptionBox;