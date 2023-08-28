import { OptionType } from '../type';
import DetailOption from './DetailOption';

interface DetailOptionBoxProps {
  option: OptionType;
}
function DetailOptionBox({ option }: DetailOptionBoxProps) {
  return (
    <DetailOption>
      <DetailOption.Header option={option}></DetailOption.Header>
      <DetailOption.List option={option}></DetailOption.List>
    </DetailOption>
  );
}

export default DetailOptionBox;
