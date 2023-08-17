import { useState } from 'react';
import DetailOption from './DetailOption';
import { mockBasicOptions } from '@/assets/mock/mock';
import { BasicOptions } from '@/constant';
import { BasicOptionFilterType } from '@/types';

function DetailBasicOptionBox() {
  const [selectedOptionFilter, setSelectedOptionFilter] =
    useState<BasicOptionFilterType>('전체');

  function isSelectOption(categoryId: number) {
    return (
      BasicOptions[selectedOptionFilter] === -1 ||
      BasicOptions[selectedOptionFilter] === categoryId
    );
  }

  return (
    <DetailOption>
      <DetailOption.Header
        option={{ type: '기본 포함 품목', price: 0 }}
      ></DetailOption.Header>
      <DetailOption.Tabs
        options={Object.keys(BasicOptions) as BasicOptionFilterType[]}
        selectedOption={selectedOptionFilter}
        onClick={(option) => setSelectedOptionFilter(option)}
      />
      {mockBasicOptions.contents
        .filter((option) => isSelectOption(option.categoryId!))
        .map((option) => (
          <DetailOption.List
            option={{ ...option, type: '기본 포함 품목' }}
            key={option.name}
          />
        ))}
    </DetailOption>
  );
}

export default DetailBasicOptionBox;
