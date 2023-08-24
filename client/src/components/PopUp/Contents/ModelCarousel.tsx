import { useState } from 'react';
import { hydrogenElectricCars } from '@/assets/mock/ModelListMock';
import PopUp from '..';

function ModelCarousel() {
  const itemsPerSlide = 2;
  const listLength = hydrogenElectricCars.length;
  const pages = Math.ceil(listLength / itemsPerSlide);
  const [currentSlide, setCurrentSlide] = useState(0);
  const [activeIndex, setactiveIndex] = useState(-1);

  const modelCards = Array.from({ length: listLength }, (_, i) => (
    <PopUp.ModelSelectCard
      key={`modelSelectCard-${i}`}
      index={i}
      activeIndex={activeIndex}
      onClick={() => {
        setactiveIndex(i);
      }}
    ></PopUp.ModelSelectCard>
  ));

  const startIndex = currentSlide * itemsPerSlide;
  const endIndex = startIndex + itemsPerSlide;
  const visibleItems = modelCards.slice(startIndex, endIndex);
  const dotIndicator = Array.from({ length: pages }, (_, i) => (
    <div
      key={i}
      className={`w-8px h-8px rounded-6px bg-grey-002 ${
        currentSlide === i ? 'bg-grey-004' : ''
      }`}
      onClick={() => moveToAnotherSlide(i)}
      role="none"
    ></div>
  ));

  function moveToAnotherSlide(index: number) {
    setCurrentSlide(index);
  }

  return (
    <div>
      <div className="transition-transform duration-500 ease-in-out max-h-250px">
        {visibleItems.map((modelCard, index) => (
          <div key={index}>{modelCard}</div>
        ))}
      </div>
      <div className="flex justify-center gap-8px mt-12px">
        {dotIndicator.map((item) => (
          <>{item}</>
        ))}
      </div>
    </div>
  );
}

export default ModelCarousel;
