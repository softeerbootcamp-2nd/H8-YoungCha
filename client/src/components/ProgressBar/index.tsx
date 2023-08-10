import { useState } from 'react';
import ProgressItem from './ProgressItem';
import SelectedBar from './SelectedBar';

function ProgressBar() {
  const progressItems = [
    '파워트레인',
    '구동 방식',
    '바디 타입',
    '외장 색상',
    '내장 색상',
    '휠 선택',
    '옵션 선택',
    '견적 내기',
  ];
  const [selectedCategory, setSelectedCategory] = useState(0);

  function handleCategoryClick(index: number) {
    setSelectedCategory(index);
  }

  function makeCategory() {
    return progressItems.map((item: string, index: number) => (
      <ProgressItem
        key={index}
        itemIndex={index + 1}
        itemName={item}
        isSelected={index === selectedCategory}
        onClick={() => handleCategoryClick(index)}
      />
    ));
  }

  return (
    <nav className="min-w-1024px text-center h-26px title5">
      <span className="relative mx-auto whitespace-nowrap">
        {makeCategory()}
        <SelectedBar active={selectedCategory} />
      </span>
      <div className="w-full h-0.5 absolute top-23px bg-grey-002" />
    </nav>
  );
}

export default ProgressBar;
