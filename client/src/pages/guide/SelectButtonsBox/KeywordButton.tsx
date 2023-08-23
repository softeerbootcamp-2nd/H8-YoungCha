import SelectButton from '@/components/SelectButton';
import StepCircle from '../StepCircle';
import { InactiveRound } from '@/assets/icons';
import { useTagSelectContext } from '@/store/useTagSelectContext';

interface KeywordButtonProps {
  keyword: string;
  keywordIndex: number;
  hoveredKeyword: string;
  isSelected: boolean;
  handleClickKeyword: (keyword: string) => void;
  setHoveredKeyword: (keyword: string) => void;
}
function KeywordButton({
  keyword,
  keywordIndex,
  hoveredKeyword,
  isSelected,
  handleClickKeyword,
  setHoveredKeyword,
}: KeywordButtonProps) {
  const { selectedKeyword } = useTagSelectContext();
  const isHovered = hoveredKeyword === keyword && selectedKeyword.length < 3;

  return (
    <SelectButton
      size="md"
      type={isSelected || isHovered ? 'iconActive' : 'default'}
      key={keyword}
      onClick={() => handleClickKeyword(keyword)}
      onMouseEnter={() => setHoveredKeyword(keyword)}
      onMouseLeave={() => setHoveredKeyword('')}
    >
      {keyword}
      {isSelected ? (
        <StepCircle size="sm" isActive={true} step={keywordIndex + 1} />
      ) : isHovered ? (
        <StepCircle
          size="sm"
          isActive={true}
          step={selectedKeyword.length + 1}
        />
      ) : (
        <InactiveRound />
      )}
    </SelectButton>
  );
}

export default KeywordButton;
