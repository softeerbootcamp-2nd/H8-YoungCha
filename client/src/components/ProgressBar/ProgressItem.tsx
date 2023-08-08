interface ProgressItemProps {
  itemIndex: number;
  itemName: string;
  isSelected: boolean;
  onClick: () => void;
}

function ProgressItem({
  itemIndex,
  itemName,
  isSelected,
  onClick,
}: ProgressItemProps) {
  function handleOnClick() {
    onClick();
  }

  return (
    <button onClick={handleOnClick}>
      <p
        className={`w-120px ${
          isSelected ? 'text-main-blue' : 'text-grey-002'
        } ${isSelected ? 'font-medium' : 'font-normal'}`}
      >{`0${itemIndex} ${itemName}`}</p>
    </button>
  );
}

export default ProgressItem;
