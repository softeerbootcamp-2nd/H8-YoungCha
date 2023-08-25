import { useState } from 'react';
import OptionLayout from './OptionLayout';
import SelectButton from '@/components/SelectButton';
import Button from '@/components/Button';
import { MoreViewArrow } from '@/assets/icons';
import { BasicOptionFilterType, TrimType } from '@/types';
import { BasicOptionsId } from '@/constant';
import { TITLE } from './constant';
import BasicOptionLists from './BasicOptionLists';

interface BasicOptionBoxProps {
  trims: TrimType[];
}
function BasicOptionBox({ trims }: BasicOptionBoxProps) {
  const [selectedOption, setSelectedOption] =
    useState<BasicOptionFilterType>('전체');
  const [currentSize, setCurrentSize] = useState(5);
  const [lastPageCount, setLastPageCount] = useState(0);

  function handleMoreOptionClick() {
    setCurrentSize((prev) => prev + 5);
  }
  const basicOptionList = Object.keys(
    BasicOptionsId
  ) as BasicOptionFilterType[];

  return (
    <div className="flex flex-col gap-16px max-w-7xl">
      <h3 className="font-medium text-center text-grey-black py-8px">
        {TITLE.BASIC_OPTION}
      </h3>
      <div className="flex flex-col items-center gap-32px">
        <div className="flex justify-center gap-8px">
          {basicOptionList.map((option) => (
            <SelectButton
              key={option}
              type={selectedOption === option ? 'active' : 'default'}
              onClick={() => {
                setCurrentSize(5);
                setLastPageCount(0);
                setSelectedOption(option);
              }}
            >
              {option}
            </SelectButton>
          ))}
        </div>
        <OptionLayout>
          <div className="flex justify-around gap-8px">
            {trims.map(({ id }, index) => (
              <ul
                className="flex flex-col w-full gap-24px"
                key={`basic-option-${index}`}
              >
                <BasicOptionLists
                  id={id}
                  categoryId={BasicOptionsId[selectedOption]}
                  currentSize={currentSize}
                  setIsLastPage={setLastPageCount}
                />
              </ul>
            ))}
          </div>
        </OptionLayout>
        {lastPageCount < 3 && (
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
