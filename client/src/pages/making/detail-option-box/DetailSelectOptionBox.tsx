import { useContext, useState } from 'react';
import DetailOption from './DetailOption';
import { SelectOptionsId } from '@/constant';
import { SelectOptionFilterType } from '@/types';
import { UserSelectedOptionDataContext } from '..';

function DetailSelectOptionBox() {
  const [selectedOptionFilter, setSelectedOptionFilter] =
    useState<SelectOptionFilterType>('전체');
  const { userSelectedOptionData } = useContext(UserSelectedOptionDataContext);
  function isSelectOption(categoryId: number) {
    return (
      SelectOptionsId[selectedOptionFilter] === 0 ||
      SelectOptionsId[selectedOptionFilter] === categoryId
    );
  }
  const totalPrice = userSelectedOptionData.selectedOptions.options.reduce(
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
      {userSelectedOptionData.selectedOptions.options
        .filter((option) => isSelectOption(option.categoryId!))
        .map((option) => (
          <DetailOption.List option={option} key={option.name} />
        ))}
    </DetailOption>
  );
}

export default DetailSelectOptionBox;
