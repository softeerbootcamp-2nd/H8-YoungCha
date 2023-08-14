import ProgressItem from './ProgressItem';
import SelectedBar from './SelectedBar';

interface ProgressBarProps {
  step: number;
  path: 'self' | 'guide';
  id: string;
}

function ProgressBar({ step, path, id }: ProgressBarProps) {
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

  function makeCategory() {
    return progressItems.map((item: string, index: number) => (
      <ProgressItem
        key={index}
        itemIndex={index + 1}
        itemName={item}
        isSelected={index + 1 === step}
        url={`/model/${id}/making/${path}/${index + 1}`}
      />
    ));
  }

  return (
    <nav className="relative z-10 text-center min-w-1024px h-26px title5">
      <span className="relative mx-auto whitespace-nowrap">
        {makeCategory()}
        <SelectedBar active={step - 1} />
      </span>
      <div className="w-full h-0.5 absolute top-22px bg-grey-002" />
    </nav>
  );
}

export default ProgressBar;
