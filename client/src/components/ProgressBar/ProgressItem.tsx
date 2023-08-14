import { Link } from 'react-router-dom';

interface ProgressItemProps {
  itemIndex: number;
  itemName: string;
  isSelected: boolean;
  url: string;
}

function ProgressItem({
  itemIndex,
  itemName,
  isSelected,
  url,
}: ProgressItemProps) {
  return (
    <Link to={url}>
      <button>
        <p
          className={`w-120px ${
            isSelected ? 'text-main-blue' : 'text-grey-002'
          } ${isSelected ? 'font-medium' : 'font-normal'}`}
        >{`0${itemIndex} ${itemName}`}</p>
      </button>
    </Link>
  );
}

export default ProgressItem;
