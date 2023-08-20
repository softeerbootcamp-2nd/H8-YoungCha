import { useState } from 'react';
import DetailOption from './DetailOption';
import { mockUserSelectedOptionData } from '@/assets/mock/mock';
import { SelectOptionsId } from '@/constant';
import { SelectOptionFilterType } from '@/types';

function DetailSelectOptionBox() {
  const [selectedOptionFilter, setSelectedOptionFilter] =
    useState<SelectOptionFilterType>('전체');

  function isSelectOption(categoryId: number) {
    return (
      SelectOptionsId[selectedOptionFilter] === -1 ||
      SelectOptionsId[selectedOptionFilter] === categoryId
    );
  }
  const totalPrice = mockUserSelectedOptionData.selectedOptions.options.reduce(
    (sum, option) => sum + option.price!,
    0
  );

  return (
    <DetailOption>
      <DetailOption.Header
        option={{ type: '선택 옵션', price: totalPrice }}
      ></DetailOption.Header>
      <DetailOption.Tabs
        options={Object.keys(SelectOptionsId) as SelectOptionFilterType[]}
        selectedOption={selectedOptionFilter}
        onClick={(option) => setSelectedOptionFilter(option)}
      />
      {mockUserSelectedOptionData.selectedOptions.options
        .filter((option) => isSelectOption(option.categoryId!))
        .map((option) => (
          <DetailOption.List option={option} key={option.name} />
        ))}
    </DetailOption>
  );
}

export default DetailSelectOptionBox;
