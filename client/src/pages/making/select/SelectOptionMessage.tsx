import { OPTION_ORDER } from '../constant';

interface SelectOptionMessageProps {
  step: number;
}

function SelectOptionMessage({ step }: SelectOptionMessageProps) {
  return (
    <div className="font-hsans-head text-24px tracking-[-0.96px] pt-64px pb-32px px-32px">
      <span className="font-medium">{OPTION_ORDER[step - 1]}</span>
      <span>을 선택해주세요.</span>
    </div>
  );
}

export default SelectOptionMessage;
