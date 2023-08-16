import SelectButton from '@/components/SelectButton';
import StepCircle from '../StepCircle';
import { InactiveRound } from '@/assets/icons';

interface KeywordButtonProps {
  keyword: string;
  selectedKeyword: Array<string>;
  hoveredKeyword: string;
  handleClickKeyword: (keyword: string) => void;
  setHoveredKeyword: (keyword: string) => void;
}
function KeywordButton({
  keyword,
  selectedKeyword,
  hoveredKeyword,
  handleClickKeyword,
  setHoveredKeyword,
}: KeywordButtonProps) {
  const keywordIndex = selectedKeyword.indexOf(keyword);
  const isSelected = keywordIndex !== -1;
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
