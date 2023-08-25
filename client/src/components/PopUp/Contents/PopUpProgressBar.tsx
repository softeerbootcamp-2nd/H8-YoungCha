import { ReactNode, useState } from 'react';
import { MODEL_LIST } from '../constant';

interface PopUpProgressItemProps {
  children: ReactNode;
  active: boolean;
  onClick: () => void;
}

function PopUpProgressBar() {
  const [selectedCategory, setSelectedCategory] = useState(0);

  function handleOnClick(index: number) {
    setSelectedCategory(index);
  }

  function PopUpProgressItem({
    children,
    active = false,
    onClick,
  }: PopUpProgressItemProps) {
    return (
      <button
        className={`flex justify-center items-center h-26px ${
          active ? 'text-main-blue font-medium' : 'text-grey-002'
        }`}
        onClick={onClick}
      >
        {children}
      </button>
    );
  }

  function PopUpProgressList() {
    return MODEL_LIST.map((item: string, index: number) => (
      <PopUpProgressItem
        active={index === selectedCategory}
        key={`PopUpProgressItem-${index}`}
        onClick={() => handleOnClick(index)}
      >
        {item}
      </PopUpProgressItem>
    ));
  }

  return (
    <>
      <nav className="overflow-hidden overflow-x-auto text-center w-343px title5 border-b-2px border-grey-002 mt-24px mb-4px">
        <div className="mx-auto">
          <div className="flex whitespace-nowrap gap-24px px-16px">
            <PopUpProgressList />
          </div>
        </div>
      </nav>
    </>
  );
}

export default PopUpProgressBar;
