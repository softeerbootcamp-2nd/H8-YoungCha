import SelectButton from '@/components/SelectButton';
import { BasicOptionFilterType, SelectOptionFilterType } from '@/types';

interface DetailOptionTabsProps<T> {
  options: Array<T>;
  selectedOption: T;
  onClick: (option: T) => void;
}
function DetailOptionTabs<
  T extends BasicOptionFilterType | SelectOptionFilterType,
>({ options, selectedOption, onClick }: DetailOptionTabsProps<T>) {
  return (
    <div className="flex gap-8px">
      {options.map((option) => {
        const buttonType = selectedOption === option ? 'active' : 'default';
        return (
          <SelectButton
            type={buttonType}
            key={option}
            onClick={() => onClick(option)}
          >
            {option}
          </SelectButton>
        );
      })}
    </div>
  );
}

export default DetailOptionTabs;
