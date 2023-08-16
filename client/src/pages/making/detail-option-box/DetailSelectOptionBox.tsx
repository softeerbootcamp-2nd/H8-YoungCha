import { useState } from 'react';
import DetailOption from './DetailOption';
import { mockUserSelectedOptionData } from '@/assets/mock/mock';
import { SelectOptions } from '@/constant';
import { SelectOptionFilterType } from '@/types';

function DetailSelectOptionBox() {
  const [selectedOptionFilter, setSelectedOptionFilter] =
    useState<SelectOptionFilterType>('전체');

  function isSelectOption(categoryId: number) {
    return (
      SelectOptions[selectedOptionFilter] === -1 ||
      SelectOptions[selectedOptionFilter] === categoryId
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
        options={Object.keys(SelectOptions) as SelectOptionFilterType[]}
        selectedOption={selectedOptionFilter}
        onClick={(option) => setSelectedOptionFilter(option)}
      />
      {mockUserSelectedOptionData.selectedOptions.options.map(
        (option) =>
          isSelectOption(option.categoryId!) && (
            <DetailOption.List option={option} key={option.name} />
          )
      )}
    </DetailOption>
  );
}

export default DetailSelectOptionBox;
