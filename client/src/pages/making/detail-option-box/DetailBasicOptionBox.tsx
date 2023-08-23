import { useState } from 'react';
import DetailOption from './DetailOption';
import { BasicOptionsId } from '@/constant';
import { BasicOptionFilterType } from '@/types';
import Button from '@/components/Button';
import { MoreViewArrow } from '@/assets/icons';
import useBasicOption from '@/hooks/useBasicOption';

const OPTION_SIZE = 5;

function DetailBasicOptionBox() {
  const [selectedOptionFilter, setSelectedOptionFilter] =
    useState<BasicOptionFilterType>('전체');
  const [currentSize, setCurrentSize] = useState(OPTION_SIZE);
  const { basicOptions } = useBasicOption({
    id: 2,
    categoryId: BasicOptionsId[selectedOptionFilter],
    currentSize,
  });

  function handleMoreOptionClick() {
    setCurrentSize((prev) => prev + OPTION_SIZE);
  }

  function isSelectOption(categoryId: number) {
    return (
      BasicOptionsId[selectedOptionFilter] === -1 ||
      BasicOptionsId[selectedOptionFilter] === categoryId
    );
  }
  console.log(basicOptions);
  return (
    <DetailOption>
      <DetailOption.Header
        option={{ type: '기본 포함 품목', price: 0 }}
      ></DetailOption.Header>
      <DetailOption.Tabs
        options={Object.keys(BasicOptionsId) as BasicOptionFilterType[]}
        selectedOption={selectedOptionFilter}
        onClick={(option) => setSelectedOptionFilter(option)}
      />
      {basicOptions?.contents
        ?.filter((option) => isSelectOption(option.categoryId!))
        .map((option) => (
          <DetailOption.List
            option={{ ...option, type: '기본 포함 품목' }}
            key={option.name}
          />
        ))}

      {!basicOptions.last && (
        <div className="flex justify-center">
          <Button color="grey" onClick={handleMoreOptionClick}>
            더보기
            <MoreViewArrow />
          </Button>
        </div>
      )}
    </DetailOption>
  );
}

export default DetailBasicOptionBox;
